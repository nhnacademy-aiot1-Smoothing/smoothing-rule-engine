package live.smoothing.ruleengine.broker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerGenerateRequest {

    private String brokerIp;
    private int brokerPort;
    private String brokerName;
    private String brokerType;
}
