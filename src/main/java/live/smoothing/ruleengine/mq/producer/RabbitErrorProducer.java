package live.smoothing.ruleengine.mq.producer;

import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.sensor.dto.SensorErrorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitErrorProducer implements ErrorProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String brokerErrorQueue = "add-brokerError-queue";
    private final String sensorErrorQueue = "add-sensorError-queue";

    @Override
    public void sendBrokerError(BrokerErrorRequest brokerErrorRequest) {
        rabbitTemplate.convertAndSend(brokerErrorQueue, brokerErrorRequest);
    }

    @Override
    public void sendSensorError(SensorErrorRequest sensorErrorRequest) {
        rabbitTemplate.convertAndSend(sensorErrorQueue, sensorErrorRequest);
    }
}
