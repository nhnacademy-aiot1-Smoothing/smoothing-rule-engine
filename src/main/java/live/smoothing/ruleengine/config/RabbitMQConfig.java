package live.smoothing.ruleengine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Bean
    public DirectExchange brokerDirectExchange() {
        return new DirectExchange("broker-direct-exchange");
    }

    @Bean
    public Queue addBrokerQueue() {
        return new Queue("add-broker-queue");
    }

    @Bean
    public Queue deleteBrokerQueue() {
        return new Queue("delete-broker-queue");
    }

    @Bean
    public Binding addBrokerBinding() {
        return BindingBuilder.bind(addBrokerQueue())
                .to(brokerDirectExchange())
                .with("add-broker");
    }

    @Bean
    public Binding deleteBrokerBinding() {
        return BindingBuilder.bind(deleteBrokerQueue())
                .to(brokerDirectExchange())
                .with("delete-broker");
    }

    @Bean
    public DirectExchange topicDirectExchange() {
        return new DirectExchange("topic-direct-exchange");
    }

    @Bean
    public Queue addTopicQueue() {
        return new Queue("add-topic-queue");
    }

    @Bean
    public Queue deleteTopicQueue() {
        return new Queue("delete-topic-queue");
    }

    @Bean
    public Binding addTopicBinding() {
        return BindingBuilder.bind(addTopicQueue())
                .to(topicDirectExchange())
                .with("add-topic");
    }

    @Bean
    public Binding deleteTopicBinding() {
        return BindingBuilder.bind(deleteTopicQueue())
                .to(topicDirectExchange())
                .with("delete-topic");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory stringContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new SimpleMessageConverter());
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsonContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }
}