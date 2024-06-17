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
import org.springframework.context.annotation.Profile;

@Slf4j
@RequiredArgsConstructor
public class RabbitNodeProducer implements NodeProducer{

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    @Override
    public void sendNodeMessage(String key, SensorMessage message) {
        log.info("send message to rabbitmq : {}", gson.toJson(message.getSensorAttributes()));
        rabbitTemplate.send(key, new Message(gson.toJson(message.getSensorAttributes()).getBytes()));
    }

    @Override
    public void generateQueue(String key) {
        try{
        rabbitTemplate.execute(channel -> {
            channel.queueDeclare(key, true, false, false, null);
            return null;
        });
        }catch (Exception e){
            log.error("generate queue error : {}", e.getMessage());
        }
    }
}
