package live.smoothing.ruleengine.mq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RequiredArgsConstructor
public class RabbitNodeProducer implements NodeProducer{

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendNodeMessage(String key, String message) throws JsonProcessingException {
        log.info("send message to rabbitmq : {}", message);
//        rabbitTemplate.convertAndSend(key, objectMapper.writeValueAsString(message));
    }
}
