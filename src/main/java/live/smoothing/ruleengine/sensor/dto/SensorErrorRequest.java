package live.smoothing.ruleengine.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SensorErrorRequest {

    private String sensorErrorType;
    private LocalDateTime createdAt;
    private Double sensorValue;
    private Integer sensorId;
    private String topic;
}
