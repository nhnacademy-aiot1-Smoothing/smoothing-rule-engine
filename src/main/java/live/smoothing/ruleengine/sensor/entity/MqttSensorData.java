package live.smoothing.ruleengine.sensor.entity;

public class MqttSensorData implements SensorData {

    private final String topic;
    private final String payload;
    private final Integer brokerId;
    private final String brokerType;

    public MqttSensorData(String topic, String payload, Integer brokerId, String brokerType) {

        this.topic = topic;
        this.payload = payload;
        this.brokerId = brokerId;
        this.brokerType = brokerType;
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
    public Integer getBrokerId() {
        return brokerId;
    }

    @Override
    public String getBrokerType() {
        return brokerType;
    }
}
