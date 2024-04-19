package live.smoothing.ruleengine.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "add-broker-queue", containerFactory = "stringContainerFactory")
    public void receiveAddBrokerMessage(String message) {
        log.info("Received Add Broker message: {}", message);
    }

    @RabbitListener(queues = "delete-broker-queue", containerFactory = "stringContainerFactory")
    public void receiveDeleteBrokerMessage(String message) {
        log.info("Received Delete Broker message: {}", message);
    }

    @RabbitListener(queues = "add-topic-queue", containerFactory = "stringContainerFactory")
    public void receiveAddTopicMessage(String message) {
        log.info("Received Add Topic message: {}", message);
    }

    @RabbitListener(queues = "delete-topic-queue", containerFactory = "stringContainerFactory")
    public void receiveDeleteTopicMessage(String message) {
        log.info("Received Delete Topic message: {}", message);
    }
}
