package live.smoothing.ruleengine.sensor.service.entity;

public interface SensorData {

    String getTopic();

    String getPayload();

    void setTopic(String topic);

    void setPayload(String payload);
}
