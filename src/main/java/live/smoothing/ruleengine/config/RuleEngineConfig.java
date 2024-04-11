package live.smoothing.ruleengine.config;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import live.smoothing.ruleengine.mq.consumer.GatewayConsumerFactory;
import live.smoothing.ruleengine.mq.consumer.MqttGatewayConsumerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RuleEngineConfig {
    private final GatewayService gatewayService;

    @Bean
    public GatewayConsumerFactory gatewayConsumerFactory() {
        return new MqttGatewayConsumerFactory();
    }

    @Bean
    public RuleEngineManagement ruleEngineManagement() {

        return new RuleEngineManagement(gatewayService, gatewayConsumerFactory());
    }
}
