package live.smoothing.ruleengine.node;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.ruleengine.mq.producer.NodeProducer;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class MQNode extends Node{

    private final String key;
    private final NodeProducer nodeProducer;

    public MQNode(String nodeId,
                     String key,
                     NodeProducer nodeProducer) {
        super(nodeId, 0);
        this.key = key;
    this.nodeProducer = nodeProducer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();
                message.addAttribute("time", LocalDateTime.now().toString());
                message.addAttribute("value",String.valueOf(message.getAttribute("value")));
                nodeProducer.sendNodeMessage(key, message);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                log.debug("ReceiverNode에서 메시지를 가져오는데 실패했습니다.");
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                log.debug("메시지를 json으로 변환하는데 실패했습니다.");
            }
        }
    }
}
