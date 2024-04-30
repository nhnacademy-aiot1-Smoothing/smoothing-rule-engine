package live.smoothing.ruleengine.broker.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerAddRequest {

    private String brokerIp;
    private int brokerPort;
    private Integer brokerId;
    private String protocolType;
}
