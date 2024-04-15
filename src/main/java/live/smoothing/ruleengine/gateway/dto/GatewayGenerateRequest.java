package live.smoothing.ruleengine.gateway.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatewayGenerateRequest {

    private String gatewayIp;

    private int gatewayPort;

    private String gatewayName;

    private String gatewayType;
}
