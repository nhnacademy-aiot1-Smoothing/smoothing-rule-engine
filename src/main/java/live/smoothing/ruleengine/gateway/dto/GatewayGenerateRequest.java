package live.smoothing.ruleengine.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GatewayGenerateRequest {
    private String gatewayIp;
    private int gatewayPort;
    private String gatewayName;
    private String gatewayType;
}
