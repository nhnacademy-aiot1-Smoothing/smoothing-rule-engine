package live.smoothing.ruleengine;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.broker.service.BrokerService;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumer;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.mq.producer.ErrorProducer;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import live.smoothing.ruleengine.sensor.dto.SensorErrorRequest;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.service.SensorService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;

/**
 * RuleEngineManagement 를 관리하는 클래스
 *
 * @author 우혜승
 */
@Slf4j
public class RuleEngineManagement {

    private final Map<Integer, BrokerConsumer> brokerConsumers = new HashMap<>();
    private final Map<Integer, List<String>> topics = new HashMap<>();
    private final BrokerService brokerService;
    private final SensorService sensorService;
    private final BrokerConsumerFactory brokerConsumerFactory;
    private final NodeManager nodeManager;
    private final ErrorProducer errorProducer;

    public RuleEngineManagement(BrokerService brokerService, SensorService sensorService, BrokerConsumerFactory brokerConsumerFactory, NodeManager nodeManager, ErrorProducer errorProducer) {

        this.sensorService = sensorService;
        this.brokerService = brokerService;
        this.brokerConsumerFactory = brokerConsumerFactory;
        this.nodeManager = nodeManager;
        this.errorProducer = errorProducer;

        try {
            synchronize();
        } catch (Exception e) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(10000);
//                        synchronize();
//                    } catch (Exception e) {
//                        log.error("RuleEngineManagement init retry error", e);
//                        run();
//                    }
//                }
//            });
//            thread.start();
        }
        log.info("RuleEngineManagement create success");
    }

    /**
     * Device Service에서 Broker및 그에 속한 Topic들을 가져와 RuleEngine에 동기화하는 메소드
     */
    private void synchronize() throws Exception {

        List<BrokerResponseDto> brokerResponseDtos = sensorService.getBrokerGenerateRequest();

        for (BrokerResponseDto brokerResponseDto : brokerResponseDtos) {
            BrokerGenerateRequest brokerGenerateRequest = BrokerGenerateRequest.builder()
                    .brokerIp(brokerResponseDto.getBrokerIp())
                    .brokerPort(brokerResponseDto.getBrokerPort())
                    .brokerId(brokerResponseDto.getBrokerId())
                    .protocolType(brokerResponseDto.getProtocolType())
                    .build();
            addBroker(brokerGenerateRequest);
            Set<String> topics = brokerResponseDto.getTopics();
            for (String topic : topics) {
                subscribe(brokerResponseDto.getBrokerId(), topic);
            }
        }
        log.info("RuleEngineManagement init success");
    }

    /**
     * Broker 에서 받은 데이터를 처리하는 메소드
     *
     * @param sensorData Broker 에서 받은 데이터
     */
    public void consume(SensorData sensorData) {
        // RuleEngine 에서 데이터를 처리하는 로직
        nodeManager.putToReceiver(sensorData);
    }

    /**
     * Broker 에서 Topic 을 구독하는 메소드
     *
     * @param brokerId Broker ID
     * @param topic    Topic
     */
    public void subscribe(Integer brokerId, String topic) throws Exception {

        BrokerConsumer brokerConsumer = brokerConsumers.get(brokerId);

        if(isNull(brokerConsumer)) {
            throw new RuntimeException("Broker is not exist");
        }

        brokerConsumer.subscribe(topic);

        if (topics.get(brokerId) == null) {
            topics.put(brokerId, new LinkedList<>(Collections.singletonList(topic)));
        } else {
            topics.get(brokerId).add(topic);
        }
        log.info("topic subscribe success");
    }

    /**
     * Broker 에서 Topic 을 구독 취소하는 메소드
     *
     * @param brokerId Broker ID
     * @param topic    Topic
     */
    public void unsubscribe(Integer brokerId, String topic) throws Exception {
        BrokerConsumer brokerConsumer = brokerConsumers.get(brokerId);

        if(isNull(brokerConsumer)) {
            throw new RuntimeException("Broker is not exist");
        }

        brokerConsumer.unsubscribe(topic);
        topics.get(brokerId).remove(topic);
        log.info("topic unsubscribe success");
    }

    /**
     * Broker 를 추가하는 메소드
     *
     * @param request Broker 정보
     */
    public void addBroker(BrokerGenerateRequest request) {

        BrokerConsumer brokerConsumer = brokerConsumerFactory.create(request.getBrokerIp(), request.getBrokerPort(), request.getBrokerId(), request.getProtocolType());
        try {
            brokerConsumer.start();
            brokerConsumers.put(request.getBrokerId(), brokerConsumer);
            brokerConsumer.setRuleEngineManagement(this);
            topics.put(request.getBrokerId(), new LinkedList<>());

        } catch (Exception e) {
            log.error("Broker start error", e);
            sendBrokerError(BrokerErrorRequest.builder()
                    .brokerErrorType("연결실패")
                    .brokerId(request.getBrokerId())
                    .build());
        }
        log.info("Broker add success");
    }


    /**
     * Broker 를 제거하는 메소드
     *
     * @param brokerId Broker ID
     */
    public void removeBroker(Integer brokerId) {

        if(topics.get(brokerId) != null) {
            BrokerConsumer brokerConsumers = this.brokerConsumers.get(brokerId);
            topics.get(brokerId).forEach(topic -> {
                try {
                    brokerConsumers.unsubscribe(topic);
                } catch (Exception e) {
                    log.error("unsubscribe error", e);
                    SendSensorError(SensorErrorRequest.builder()
                            .sensorErrorType("구독해제실패")
                            .createdAt(LocalDateTime.now())
                            .topic(topic)
                            .build());
                }
            });
            topics.remove(brokerId);
        }
        try {
            brokerConsumers.get(brokerId).stop();
        } catch (Exception e) {
            log.error("Broker stop error", e);
            sendBrokerError(BrokerErrorRequest.builder()
                    .brokerErrorType("연결해제실패")
                    .brokerId(brokerId)
                    .build());
        }
        brokerConsumers.remove(brokerId);
        log.info("Broker remove success");
    }

    /**
     * Broker 에러를 추가하는 메소드
     *
     * @param request Broker 에러 정보
     */
    public void sendBrokerError(BrokerErrorRequest request) {
        errorProducer.sendBrokerError(request);
    }

    /**
     * Sensor 에러를 추가하는 메소드
     *
     * @param request Sensor 에러 정보
     */
    public void SendSensorError(SensorErrorRequest request) {
        errorProducer.sendSensorError(request);
    }
}
