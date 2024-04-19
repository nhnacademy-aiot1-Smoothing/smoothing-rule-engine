package live.smoothing.ruleengine.broker.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerGenerateRequest {

    private String brokerIp;
    private int brokerPort;
    private Integer brokerId;
    private String protocolType;
}
