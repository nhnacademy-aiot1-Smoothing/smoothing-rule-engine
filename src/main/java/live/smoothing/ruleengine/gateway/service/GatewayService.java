package live.smoothing.ruleengine.gateway.service;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.entity.Gateway;

import java.util.List;

public interface GatewayService {

    void addGateway(GatewayGenerateRequest request);

    String getGatewayName(Integer gatewayId);

    List<Gateway> getGateways();
}
