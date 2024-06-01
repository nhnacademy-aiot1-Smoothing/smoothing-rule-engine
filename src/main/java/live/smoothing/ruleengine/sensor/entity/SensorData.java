package live.smoothing.ruleengine.sensor.entity;

/**
 * Sensor 에서 발생한 데이터를 담는 인터페이스
 *
 * @author 우혜승
 */
public interface SensorData {

    String getTopic();
    String getPayload();
    Integer getBrokerId();
    String getBrokerType();
}
