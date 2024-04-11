package live.smoothing.ruleengine.gateway.service;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.entity.Gateway;

public interface GatewayService {

    void addGateway(GatewayGenerateRequest request);

    String getGatewayName(Integer gatewayId);
}
