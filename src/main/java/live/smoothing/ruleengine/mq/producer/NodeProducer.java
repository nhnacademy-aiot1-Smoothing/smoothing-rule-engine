package live.smoothing.ruleengine.mq.producer;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public interface NodeProducer {
    void sendNodeMessage(String key, SensorMessage message);
    void generateQueue(String key);
}
