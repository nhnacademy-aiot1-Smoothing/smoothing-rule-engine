package live.smoothing.ruleengine.broker.entity;

import live.smoothing.ruleengine.sensor.entity.Sensor;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "brokers")
public class Broker {

    @Id
    @Column(name = "broker_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brokerId;

    @Column(name = "broker_ip", nullable = false)
    private String brokerIp;

    @Column(name = "broker_port", nullable = false)
    private Integer brokerPort;

    @Column(name = "broker_name", nullable = false)
    private String brokerName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "protocol_type")
    private ProtocolType protocolType;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sensor> sensors;

}
