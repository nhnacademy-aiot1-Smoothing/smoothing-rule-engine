package live.smoothing.ruleengine.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gateways")
public class Gateway {

    @Id
    @Column(name = "gateway_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gatewayId;

    @Column(name = "gateway_ip", nullable = false)
    private String gatewayIp;

    @Column(name = "gateway_port", nullable = false)
    private Integer gatewayPort;

    @Column(name = "gateway_name", nullable = false)
    private String gatewayName;

    @Column(name = "gateway_type", nullable = false)
    private String gatewayType;
}
