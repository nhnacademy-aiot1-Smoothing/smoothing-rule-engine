package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.broker.dto.BrokerDeleteRequest;
import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.sensor.dto.TopicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer extends MessageQueueConsumer {

    public RabbitMQConsumer(RuleEngineManagement ruleEngineManagement) {
        super(ruleEngineManagement);
    }

    @RabbitListener(queues = "add-broker-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddBrokerMessage(BrokerGenerateRequest request) {
        ruleEngineManagement.addBroker(request);
        log.info("Received Add Broker message: {}", request);
    }

    @RabbitListener(queues = "delete-broker-queue", containerFactory = "jsonContainerFactory")
    public void receiveDeleteBrokerMessage(BrokerDeleteRequest request) {
        ruleEngineManagement.removeBroker(request.getBrokerId());
        log.info("Received Delete Broker message: {}", request);
    }

    @RabbitListener(queues = "add-topic-queue", containerFactory = "jsonContainerFactory")
    public void receiveAddTopicMessage(TopicRequest request) {
        ruleEngineManagement.subscribe(request.getBrokerId(), request.getTopic());
        log.info("Received Add Topic message: {}", request);
    }

    @RabbitListener(queues = "delete-topic-queue", containerFactory = "jsonContainerFactory")
    public void receiveDeleteTopicMessage(TopicRequest request) {
        ruleEngineManagement.unsubscribe(request.getBrokerId(), request.getTopic());
        log.info("Received Delete Topic message: {}", request);
    }
}
