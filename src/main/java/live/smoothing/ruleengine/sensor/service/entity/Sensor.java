package live.smoothing.ruleengine.sensor.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "sensors")
public class Sensor {

    @Id
    private String topic;

    @Column(name = "gateway_id")
    private Integer gatewayId;

    @Column(name = "sensor_name")
    private String sensorName;
}
