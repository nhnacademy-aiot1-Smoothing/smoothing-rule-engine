package live.smoothing.ruleengine.gateway.service.impl;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.entity.Gateway;
import live.smoothing.ruleengine.gateway.repository.GatewayRepository;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gatewayService")
@AllArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final GatewayRepository gatewayRepository;

    @Override
    public void addGateway(GatewayGenerateRequest request) {

        String ip = request.getGatewayIp();
        String name = request.getGatewayName();

        if(gatewayRepository.existsByGatewayIpOrGatewayNameExists(ip, name)) {

            Gateway gateway = new Gateway(null, request.getGatewayIp(), request.getGatewayPort(), request.getGatewayName(), request.getGatewayType());
            gatewayRepository.save(gateway);
        }
    }

    @Override
    public String getGatewayName(Integer gatewayId) {

        return gatewayRepository.findById(gatewayId)
                .get()
                .getGatewayName();
    }

    @Override
    public List<Gateway> getGateways() {

        return gatewayRepository.findAll();
    }
}
