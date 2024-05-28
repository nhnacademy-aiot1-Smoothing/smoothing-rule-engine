package live.smoothing.ruleengine.mq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NodeProducer {
    void sendNodeMessage(String key, String message) throws JsonProcessingException;
}
