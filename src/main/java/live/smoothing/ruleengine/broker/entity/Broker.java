package live.smoothing.ruleengine.broker.entity;

import live.smoothing.ruleengine.sensor.entity.Sensor;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Broker {

    private Integer brokerId;

    private String brokerIp;

    private Integer brokerPort;

    private String brokerName;

    private ProtocolType protocolType;

    private List<Sensor> sensors;
}
