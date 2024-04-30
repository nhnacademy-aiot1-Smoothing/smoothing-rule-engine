package live.smoothing.ruleengine;

import live.smoothing.ruleengine.broker.dto.BrokerAddRequest;
import live.smoothing.ruleengine.broker.service.BrokerService;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumer;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.service.SensorService;
import lombok.extern.slf4j.Slf4j;

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

    public RuleEngineManagement(BrokerService brokerService, SensorService sensorService, BrokerConsumerFactory brokerConsumerFactory, NodeManager nodeManager) {

        this.sensorService = sensorService;
        this.brokerService = brokerService;
        this.brokerConsumerFactory = brokerConsumerFactory;
        this.nodeManager = nodeManager;

        try {
            init();
        } catch (Exception e) {
            log.error("RuleEngineManagement init error", e);
        }
        //device 없이 사용할 init 주석처리 후 주석 해제
//        MqttBrokerConsumer mqttBrokerConsumer = new MqttBrokerConsumer(
//                30,
//                30,
//                true,
//                true,
//                this,
//            "133.186.153.19",
//"test",
//                1883,
//                "test"
//        );
//        try {
//            mqttBrokerConsumer.start();
//            mqttBrokerConsumer.subscribe("#");
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * 생성되었을 때 Api 에 저장된 브로커 및 센서, 토픽 정보를 가져와서 초기화
     */
    private void init() {

        List<BrokerResponseDto> brokerResponseDtos = sensorService.getBrokerGenerateRequest();

        for (BrokerResponseDto brokerResponseDto : brokerResponseDtos) {
            BrokerAddRequest brokerAddRequest = BrokerAddRequest.builder()
                    .brokerIp(brokerResponseDto.getBrokerIp())
                    .brokerPort(brokerResponseDto.getBrokerPort())
                    .brokerId(brokerResponseDto.getBrokerId())
                    .protocolType(brokerResponseDto.getProtocolType())
                    .build();
            addBroker(brokerAddRequest);
            Set<String> topics = brokerResponseDto.getTopics();
            for (String topic : topics) {
                subscribe(brokerResponseDto.getBrokerId(), topic);
            }
        }

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

        if(isNull(brokerConsumer)) {
            throw new RuntimeException("Broker is not exist");
        }

        try {
            brokerConsumer.subscribe(topic);
            if (topics.get(brokerId) == null) {
                topics.put(brokerId, new LinkedList<>(Collections.singletonList(topic)));
            } else {
                topics.get(brokerId).add(topic);
            }

        } catch (Exception e) {
            log.error("subscribe error", e);
        }
    }

    /**
     * Broker 에서 Topic 을 구독 취소하는 메소드
     *
     * @param brokerId Broker ID
     * @param topic    Topic
     */
    public void unsubscribe(Integer brokerId, String topic) {
        BrokerConsumer brokerConsumer = brokerConsumers.get(brokerId);

        if(isNull(brokerConsumer)) {
            throw new RuntimeException("Broker is not exist");
        }

        try {
            brokerConsumer.unsubscribe(topic);
            topics.get(brokerId).remove(topic);
        } catch (Exception e) {
            log.error("unsubscribe error", e);
        }
    }

    /**
     * Broker 를 추가하는 메소드
     *
     * @param request Broker 정보
     */
    public void addBroker(BrokerAddRequest request) {

        BrokerConsumer brokerConsumer = brokerConsumerFactory.create(request.getBrokerIp(), request.getBrokerPort(), request.getBrokerId(), request.getProtocolType());
        try {
            brokerConsumer.start();
            brokerConsumers.put(request.getBrokerId(), brokerConsumer);
            brokerConsumer.setRuleEngineManagement(this);
            topics.put(request.getBrokerId(), new LinkedList<>());

        } catch (Exception e) {
            log.error("Broker start error", e);
            //todo 연결 실패 mq 에러 처리
        }

    }


    /**
     * Broker 를 제거하는 메소드
     *
     * @param brokerId Broker ID
     */
    public void removeBroker(Integer brokerId) {

        if(topics.get(brokerId) != null) {
            for (String topic : topics.get(brokerId)) {
                unsubscribe(brokerId, topic);
                topics.remove(brokerId);
            }
        }
        try {
            brokerConsumers.get(brokerId).stop();
        } catch (Exception e) {
            log.error("Broker stop error", e);
            //todo 연결 해제 실패 mq 에러 처리
        }
        brokerConsumers.remove(brokerId);

    }

}
