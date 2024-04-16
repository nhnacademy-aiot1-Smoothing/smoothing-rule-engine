package live.smoothing.ruleengine;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.entity.Broker;
import live.smoothing.ruleengine.broker.service.BrokerService;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumer;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.sensor.entity.Sensor;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.service.SensorService;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class RuleEngineManagement {

    private final List<BrokerConsumer> brokerConsumers = new LinkedList<>();

    private final BrokerService brokerService;

    private final SensorService sensorService;

    private final BrokerConsumerFactory brokerConsumerFactory;

    public RuleEngineManagement(BrokerService brokerService, SensorService sensorService, BrokerConsumerFactory brokerConsumerFactory) {

        this.sensorService = sensorService;
        this.brokerService = brokerService;
        this.brokerConsumerFactory = brokerConsumerFactory;

        init();
    }

    private void init() {

        List<Broker> brokers = brokerService.getBrokers();

        for (Broker g : brokers) {
            addBroker(new BrokerGenerateRequest(g.getBrokerIp(), g.getBrokerPort(), g.getBrokerName(), g.getBrokerType()));

        }

        // 센서 목록 가져와서 각각의 게이트웨이에 추가
        for (Broker g : brokers) {

            List<Sensor> sensors = sensorService.getSensors(g.getBrokerId());

            for (Sensor s : sensors) {
                subscribe(g.getBrokerId(), s.getTopic());
            }
        }

    }

    public void consume(SensorData sensorData) {
        // RuleEngine 에서 데이터를 처리하는 로직

    }

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

    public void addBroker(BrokerGenerateRequest request) {

        brokerService.addBroker(request);
        BrokerConsumer brokerConsumer = brokerConsumerFactory.create(request.getBrokerIp(), request.getBrokerPort(), request.getBrokerName(), request.getBrokerType());
        brokerConsumers.add(brokerConsumer);

        try {
            brokerConsumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeBroker(Integer brokerId) {
        // RuleEngine 에서 Broker 를 제거하는 로직
    }
}
