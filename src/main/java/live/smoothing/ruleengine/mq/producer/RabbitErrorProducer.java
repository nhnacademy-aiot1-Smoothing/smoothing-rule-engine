package live.smoothing.ruleengine.mq.producer;

import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.sensor.dto.SensorErrorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitErrorProducer implements ErrorProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String brokerErrorQueue = "add-brokerError-queue";
    private final String sensorErrorQueue = "add-sensorError-queue";

    @Override
    public void sendBrokerError(BrokerErrorRequest brokerErrorRequest) {
        try {
            rabbitTemplate.convertAndSend(brokerErrorQueue, brokerErrorRequest);
        }catch (Exception e){
            log.error("send message error : {}", e.getMessage());
        }
    }

    @Override
    public void sendSensorError(SensorErrorRequest sensorErrorRequest) {
        try {
            rabbitTemplate.convertAndSend(sensorErrorQueue, sensorErrorRequest);
        }catch (Exception e){
            log.error("send message error : {}", e.getMessage());
        }
    }
}
