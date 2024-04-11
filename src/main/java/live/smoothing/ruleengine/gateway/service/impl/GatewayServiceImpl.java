package live.smoothing.ruleengine.gateway.service.impl;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import org.springframework.stereotype.Service;

@Service("gatewayService")
public class GatewayServiceImpl implements GatewayService {

    @Override
    public void addGateway(GatewayGenerateRequest request) {

    }

    @Override
    public String getGatewayName(Integer gatewayId) {
        return "";
    }
}
