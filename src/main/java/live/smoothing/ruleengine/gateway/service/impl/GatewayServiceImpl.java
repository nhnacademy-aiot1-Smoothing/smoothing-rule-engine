package live.smoothing.ruleengine.gateway.service.impl;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.entity.Gateway;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gatewayService")
public class GatewayServiceImpl implements GatewayService {

    @Override
    public void addGateway(GatewayGenerateRequest request) {

    }

    @Override
    public String getGatewayName(Integer gatewayId) {
        return "";
    }

    @Override
    public List<Gateway> getGateways() {
        return List.of();
    }
}
