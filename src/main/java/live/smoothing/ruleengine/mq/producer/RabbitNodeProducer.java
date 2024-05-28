package live.smoothing.ruleengine.mq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RequiredArgsConstructor
public class RabbitNodeProducer implements NodeProducer{

    private final RabbitTemplate rabbitTemplate;
    private Gson gson = new Gson();

    @Override
    public void sendNodeMessage(String key, SensorMessage message) throws JsonProcessingException {
        log.info("send message to rabbitmq : {}", gson.toJson(message.getSensorAttributes()));
        rabbitTemplate.send(key, new Message(gson.toJson(message.getSensorAttributes()).getBytes()));
    }
}
