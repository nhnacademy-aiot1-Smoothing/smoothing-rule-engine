package live.smoothing.ruleengine.broker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "broker_type", nullable = false)
    private String brokerType;
}
