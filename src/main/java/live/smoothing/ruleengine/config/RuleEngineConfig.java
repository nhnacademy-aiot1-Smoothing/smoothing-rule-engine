package live.smoothing.ruleengine.config;

import com.google.gson.Gson;
import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.mq.consumer.BrokerConsumerFactory;
import live.smoothing.ruleengine.mq.consumer.MqttBrokerConsumerFactory;
import live.smoothing.ruleengine.mq.producer.ErrorProducer;
import live.smoothing.ruleengine.mq.producer.NodeProducer;
import live.smoothing.ruleengine.mq.producer.RabbitNodeProducer;
import live.smoothing.ruleengine.node.DefaultNodeGenerator;
import live.smoothing.ruleengine.node.NodeGenerator;
import live.smoothing.ruleengine.node.NodeManager;
import live.smoothing.ruleengine.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RuleEngineConfig {

    private final SensorService sensorService;
    private final ErrorProducer errorProducer;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    @Bean
    public BrokerConsumerFactory brokerConsumerFactory() {

        return new MqttBrokerConsumerFactory();
    }

    @Bean
    public RuleEngineManagement ruleEngineManagement() {

        return new RuleEngineManagement(sensorService
                , brokerConsumerFactory()
                , nodeManager()
                , errorProducer);
    }

    @Bean
    public NodeGenerator nodeGenerator() {
        return new DefaultNodeGenerator(nodeProducer());
    }

    @Bean
    public NodeManager nodeManager(){
        return new NodeManager(nodeGenerator());
    }

    @Bean
    public NodeProducer nodeProducer() {
        return new RabbitNodeProducer(rabbitTemplate, gson);
    }
}
