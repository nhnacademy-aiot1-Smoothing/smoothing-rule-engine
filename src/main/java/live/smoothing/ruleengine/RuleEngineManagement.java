package live.smoothing.ruleengine;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumer;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.mq.producer.ErrorProducer;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import live.smoothing.ruleengine.sensor.dto.SensorErrorRequest;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.service.SensorService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

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
    private final SensorService sensorService;
    private final BrokerConsumerFactory brokerConsumerFactory;
    private final NodeManager nodeManager;
    private final ErrorProducer errorProducer;

    public RuleEngineManagement(SensorService sensorService, BrokerConsumerFactory brokerConsumerFactory, NodeManager nodeManager, ErrorProducer errorProducer) {

        this.sensorService = sensorService;
        this.brokerConsumerFactory = brokerConsumerFactory;
        this.nodeManager = nodeManager;
        this.errorProducer = errorProducer;

        try {
            synchronize();
        } catch (Exception e) {
            log.error("RuleEngineManagement init error", e);
        }
        log.info("RuleEngineManagement create success");
    }

    /**
     * Device Service에서 Broker및 그에 속한 Topic들을 가져와 RuleEngine에 동기화하는 메소드
     */
    public void synchronize() {

        List<BrokerResponseDto> brokerResponseDtos = sensorService.getBrokerGenerateRequest();

        Set<Integer> notExistBrokerIds = new HashSet<>();
        Set<Integer> newBrokerIds = brokerResponseDtos.stream().map(BrokerResponseDto::getBrokerId).collect(Collectors.toSet());
        brokerConsumers.keySet().stream().filter(brokerId -> !newBrokerIds.contains(brokerId)).forEach(notExistBrokerIds::add);
        notExistBrokerIds.forEach(this::removeBroker);

        brokerResponseDtos.forEach(brokerResponseDto -> {
            if (!notExistBrokerIds.contains(brokerResponseDto.getBrokerId())) {
                List<String> topics = this.topics.get(brokerResponseDto.getBrokerId());
                if (topics != null) {
                    Set<String> notExistTopics = new HashSet<>();
                    brokerResponseDto.getTopics().forEach(topic -> {
                        if (!topics.contains(topic)) {
                            notExistTopics.add(topic);
                        }
                    });
                    notExistTopics.forEach(topic -> unsubscribe(brokerResponseDto.getBrokerId(), topic));
                }
            }
        });

        brokerResponseDtos.forEach(brokerResponseDto -> {
            if (!brokerConsumers.containsKey(brokerResponseDto.getBrokerId())) {
                BrokerGenerateRequest brokerGenerateRequest = BrokerGenerateRequest.builder()
                        .brokerIp(brokerResponseDto.getBrokerIp())
                        .brokerPort(brokerResponseDto.getBrokerPort())
                        .brokerId(brokerResponseDto.getBrokerId())
                        .protocolType(brokerResponseDto.getProtocolType())
                        .build();
                addBroker(brokerGenerateRequest);
            }

            brokerResponseDto.getTopics().forEach(topic -> {
                if (!topics.get(brokerResponseDto.getBrokerId()).contains(topic)) {
                    subscribe(brokerResponseDto.getBrokerId(), topic);
                }
            });

        });

        log.info("RuleEngineManagement synchronize success");
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
    public void subscribe(Integer brokerId, String topic) {

        BrokerConsumer brokerConsumer = brokerConsumers.get(brokerId);

        if (isNull(brokerConsumer)) {
            log.error("Broker is not exist");
            return;
        }

        if (topics.get(brokerId) == null) {
            topics.put(brokerId, new LinkedList<>(Collections.singletonList(topic)));
        } else {
            topics.get(brokerId).add(topic);
        }

        if (brokerConsumer.isRunning()) {
            try {
                brokerConsumer.subscribe(topic);
            } catch (Exception e) {
                log.error("subscribe error", e);
                return;
            }
        }

        log.info("topic subscribe success");
    }

    /**
     * Broker 에서 Topic 을 구독 취소하는 메소드
     *
     * @param brokerId Broker ID
     * @param topic    Topic
     */
    public void unsubscribe(Integer brokerId, String topic) {
        BrokerConsumer brokerConsumer = brokerConsumers.get(brokerId);

        if (isNull(brokerConsumer)) {
            log.error("Broker is not exist");
            return;
        }

        if (brokerConsumer.isRunning()) {
            try {
                brokerConsumer.unsubscribe(topic);
            } catch (Exception e) {
                log.error("unsubscribe error", e);
                return;
            }
        }

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
        brokerConsumer.setRuleEngineManagement(this);
        brokerConsumers.put(request.getBrokerId(), brokerConsumer);
        topics.put(request.getBrokerId(), new LinkedList<>());

        try {
            brokerConsumer.start();
        } catch (Exception e) {
            log.error("Broker start error", e);
            sendBrokerError(BrokerErrorRequest.builder()
                    .brokerErrorType("통신에러")
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

        if (topics.get(brokerId) != null) {
            BrokerConsumer brokerConsumers = this.brokerConsumers.get(brokerId);
            topics.get(brokerId).forEach(topic -> {
                try {
                    brokerConsumers.unsubscribe(topic);
                } catch (Exception e) {
                    log.error("unsubscribe error", e);
                }
            });
            topics.remove(brokerId);
        }
        try {
            brokerConsumers.get(brokerId).stop();
        } catch (Exception e) {
            log.error("Broker stop error", e);
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
