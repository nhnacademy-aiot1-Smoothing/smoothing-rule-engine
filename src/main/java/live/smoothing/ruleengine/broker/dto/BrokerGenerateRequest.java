package live.smoothing.ruleengine.broker.dto;

import live.smoothing.ruleengine.broker.entity.ProtocolType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerGenerateRequest {

    private String brokerIp;
    private int brokerPort;
    private String brokerName;
    private ProtocolType protocolType;
}
