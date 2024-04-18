package live.smoothing.ruleengine;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.entity.Broker;
import live.smoothing.ruleengine.broker.service.BrokerService;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumer;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.mq.consumer.MqttBrokerConsumer;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import live.smoothing.ruleengine.sensor.entity.Sensor;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.service.SensorService;
import live.smoothing.ruleengine.sensor.entity.Topic;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.isNull;

/**
 * RuleEngineManagement 를 관리하는 클래스
 *
 * @author 우혜승
 */
@Slf4j
public class RuleEngineManagement {

    private final List<BrokerConsumer> brokerConsumers = new LinkedList<>();
    private final BrokerService brokerService;
    private final SensorService sensorService;
    private final BrokerConsumerFactory brokerConsumerFactory;
    private final NodeManager nodeManager;


    public RuleEngineManagement(BrokerService brokerService, SensorService sensorService, BrokerConsumerFactory brokerConsumerFactory, NodeManager nodeManager) {

        this.sensorService = sensorService;
        this.brokerService = brokerService;
        this.brokerConsumerFactory = brokerConsumerFactory;
        this.nodeManager = nodeManager;

        init();
        MqttBrokerConsumer mqttBrokerConsumer = new MqttBrokerConsumer(
                30,
                30,
                true,
                true,
                this,
                "133.186.153.19",
                "test",
                1883,
                "test"
        );
        try {
            mqttBrokerConsumer.start();
            mqttBrokerConsumer.subscribe("#");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 생성되었을 때 DB 에 저장된 브로커 및 센서, 토픽 정보를 가져와서 초기화
     */
    private void init() {

        List<Broker> brokers = brokerService.getBrokers();

        for (Broker g : brokers) {
            addBroker(new BrokerGenerateRequest(g.getBrokerIp(), g.getBrokerPort(), g.getBrokerName(), g.getProtocolType()));

        }

        // 센서 목록 가져와서 각각의 브로커에 추가
        for (Broker g : brokers) {

            List<Sensor> sensors = g.getSensors();

            for (Sensor s : sensors) {
                for (Topic t : s.getTopics()) {
                    subscribe(g.getBrokerId(), t.getTopic());
                }
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

        for(int i = 0;i<10;i++) {
            nodeManager.putToReceiver(sensorData);
        }
    }

    /**
     * Broker 에서 Topic 을 구독하는 메소드
     *
     * @param brokerId Broker ID
     * @param topic    Topic
     */
    public void subscribe(Integer brokerId, String topic) {

        String brokerName = brokerService.getBrokerName(brokerId);

        BrokerConsumer brokerConsumer = null;

        for (BrokerConsumer g : brokerConsumers) {
            if (brokerConsumer.getBrokerName().equals(brokerName)) {
                brokerConsumer = g;
                break;
            }
        }

        if (isNull(brokerConsumer)) {
            throw new IllegalArgumentException("BrokerConsumer not found");
        }

        try {
            brokerConsumer.subscribe(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Broker 에서 Topic 을 구독 취소하는 메소드
     *
     * @param brokerName Broker 이름
     * @param topic      Topic
     */
    public void unsubscribe(String brokerName, String topic) {
        BrokerConsumer brokerConsumer = null;
        for (BrokerConsumer g : brokerConsumers) {
            if (brokerConsumer.getBrokerName().equals(brokerName)) {
                brokerConsumer = g;
                break;
            }
        }

        if (isNull(brokerConsumer)) {
            throw new IllegalArgumentException("BrokerConsumer not found");
        }

        try {
            brokerConsumer.unsubscribe(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Broker 를 추가하는 메소드
     *
     * @param request Broker 정보
     */
    public void addBroker(BrokerGenerateRequest request) {

        brokerService.addBroker(request);
        BrokerConsumer brokerConsumer = brokerConsumerFactory.create(request.getBrokerIp(), request.getBrokerPort(), request.getBrokerName(), request.getProtocolType());
        brokerConsumers.add(brokerConsumer);

        try {
            brokerConsumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Broker 를 제거하는 메소드
     *
     * @param brokerId Broker ID
     */
    public void removeBroker(Integer brokerId) {
        // RuleEngine 에서 Broker 를 제거하는 로직
    }

    /**
     * Sensor 를 추가하는 메소드
     */
    public void addSensor() {
        // RuleEngine 에서 Sensor 를 추가하는 로직
    }

    /**
     * Sensor 를 제거하는 메소드
     */
    public void removeSensor() {
        // RuleEngine 에서 Sensor 를 제거하는 로직
    }
}
