package live.smoothing.ruleengine.sensor.entity;

public interface SensorData {

    String getTopic();

    String getPayload();

    void setTopic(String topic);

    void setPayload(String payload);
}
