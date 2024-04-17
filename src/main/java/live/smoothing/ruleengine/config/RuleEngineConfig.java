package live.smoothing.ruleengine.config;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.broker.service.BrokerService;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.mq.consumer.MqttBrokerConsumerFactory;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RuleEngineConfig {

    private final BrokerService brokerService;
    private final SensorService sensorService;
    private final NodeManager nodeManager;

    @Bean
    public BrokerConsumerFactory brokerConsumerFactory() {

        return new MqttBrokerConsumerFactory();
    }

    @Bean
    public RuleEngineManagement ruleEngineManagement() {

        return new RuleEngineManagement(brokerService,sensorService, brokerConsumerFactory(), nodeManager);
    }
}
