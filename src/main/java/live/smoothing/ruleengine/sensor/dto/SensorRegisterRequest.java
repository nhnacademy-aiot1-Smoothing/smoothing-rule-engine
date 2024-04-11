package live.smoothing.ruleengine.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorRegisterRequest {

    private String topic;

    private Integer gatewayId;

    private String sensorName;
}
