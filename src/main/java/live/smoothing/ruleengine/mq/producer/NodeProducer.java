package live.smoothing.ruleengine.mq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public interface NodeProducer {
    void sendNodeMessage(String key, SensorMessage message) throws JsonProcessingException;
}
