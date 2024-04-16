package live.smoothing.ruleengine.sensor.dto;

import live.smoothing.ruleengine.sensortype.entity.SensorType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorRegisterRequest {

    private String topic;
    private Integer brokerId;
    private String sensorName;
    private SensorType sensorType;
}
