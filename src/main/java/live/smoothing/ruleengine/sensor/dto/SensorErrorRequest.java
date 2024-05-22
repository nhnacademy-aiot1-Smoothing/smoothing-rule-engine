package live.smoothing.ruleengine.sensor.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SensorErrorRequest {

    private String sensorErrorType;
    private String createdAt;
    private Double sensorValue;
    private Integer sensorId;
    private String topic;

    @Builder
    public SensorErrorRequest(String sensorErrorType, LocalDateTime createdAt, Double sensorValue, Integer sensorId, String topic) {
        this.sensorErrorType = sensorErrorType;
        this.createdAt = createdAt.toString();
        this.sensorValue = sensorValue;
        this.sensorId = sensorId;
        this.topic = topic;
    }
}
