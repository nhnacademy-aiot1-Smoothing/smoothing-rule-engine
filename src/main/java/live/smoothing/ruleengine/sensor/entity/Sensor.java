package live.smoothing.ruleengine.sensor.entity;

import live.smoothing.ruleengine.broker.entity.Broker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    private Integer sensorId;

    private Broker broker;

    private String sensorName;

    private LocalDateTime sensorRegisteredAt;

    private SensorType sensorType;

    private List<Topic> topics;
}