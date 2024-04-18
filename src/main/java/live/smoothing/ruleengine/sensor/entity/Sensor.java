package live.smoothing.ruleengine.sensor.entity;

import live.smoothing.ruleengine.broker.entity.Broker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensors")
public class Sensor {

    @Id
    @Column(name = "sensor_id")
    private Integer sensorId;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_registered_at")
    private LocalDateTime sensorRegisteredAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type")
    private SensorType sensorType;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Topic> topics;
}
