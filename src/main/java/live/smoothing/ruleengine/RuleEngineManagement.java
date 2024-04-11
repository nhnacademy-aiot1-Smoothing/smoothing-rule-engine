package live.smoothing.ruleengine;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import live.smoothing.ruleengine.mq.consumer.GatewayConsumer;
import live.smoothing.ruleengine.mq.consumer.GatewayConsumerFactory;
import live.smoothing.ruleengine.sensor.service.entity.SensorData;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class RuleEngineManagement {
    private final List<GatewayConsumer> gatewayConsumers = new LinkedList<>();
    private final GatewayService gatewayService;
    private final GatewayConsumerFactory gatewayConsumerFactory;

    public RuleEngineManagement(GatewayService gatewayService, GatewayConsumerFactory gatewayConsumerFactory) {
        this.gatewayService = gatewayService;
        this.gatewayConsumerFactory = gatewayConsumerFactory;
    }

    public void consume(SensorData sensorData) {
        // RuleEngine 에서 데이터를 처리하는 로직
    }

    public void subscribe(Integer gatewayId, String topic) {
        String gatewayName = gatewayService.getGatewayName(gatewayId);

        GatewayConsumer gatewayConsumer = null;
        for (GatewayConsumer g : gatewayConsumers) {
            if (gatewayConsumer.getGatewayName().equals(gatewayName)) {
                gatewayConsumer = g;
                break;
            }
        }
        if (isNull(gatewayConsumer)) {
            throw new IllegalArgumentException("GatewayConsumer not found");
        }
        try {
            gatewayConsumer.subscribe(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe(String gatewayName, String topic) {
        GatewayConsumer gatewayConsumer = null;
        for (GatewayConsumer g : gatewayConsumers) {
            if (gatewayConsumer.getGatewayName().equals(gatewayName)) {
                gatewayConsumer = g;
                break;
            }
        }
        if (isNull(gatewayConsumer)) {
            throw new IllegalArgumentException("GatewayConsumer not found");
        }
        try {
            gatewayConsumer.unsubscribe(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGateway(GatewayGenerateRequest request) {
        gatewayService.addGateway(request);
        GatewayConsumer gatewayConsumer = gatewayConsumerFactory.create(request.getGatewayIp(), request.getGatewayPort(), request.getGatewayName(), request.getGatewayType());
        gatewayConsumers.add(gatewayConsumer);
        try {
            gatewayConsumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeGateway(Integer gatewayId) {
        // RuleEngine 에서 Gateway 를 제거하는 로직
    }
}
