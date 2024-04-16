package live.smoothing.ruleengine.sensor.entity;

public class MqttSensorData implements SensorData {

    private String topic;
    private String payload;

    public MqttSensorData(String topic, String payload) {

        this.topic = topic;
        this.payload = payload;
    }

    @Override
    public String getTopic() {

        return topic;
    }

    @Override
    public String getPayload() {

        return payload;
    }

    @Override
    public void setTopic(String topic) {

        this.topic = topic;
    }

    @Override
    public void setPayload(String payload) {

        this.payload = payload;
    }
}
