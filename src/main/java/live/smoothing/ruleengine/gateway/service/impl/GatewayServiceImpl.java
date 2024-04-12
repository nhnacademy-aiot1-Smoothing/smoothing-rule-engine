package live.smoothing.ruleengine.gateway.service.impl;

import live.smoothing.ruleengine.gateway.dto.GatewayGenerateRequest;
import live.smoothing.ruleengine.gateway.entity.Gateway;
import live.smoothing.ruleengine.gateway.repository.GatewayRepository;
import live.smoothing.ruleengine.gateway.service.GatewayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("gatewayService")
@AllArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final GatewayRepository gatewayRepository;

    @Override
    public Gateway addGateway(GatewayGenerateRequest request) {

        String ip = request.getGatewayIp();
        String name = request.getGatewayName();

        if(!gatewayRepository.existsByGatewayIpOrGatewayName(ip, name)) {

            Gateway gateway = new Gateway(null, request.getGatewayIp(), request.getGatewayPort(), request.getGatewayName(), request.getGatewayType());
            return gatewayRepository.save(gateway);

        }

        throw new IllegalArgumentException("게이트웨이가 이미 존재합니다.");
    }

    @Override
    public String getGatewayName(Integer gatewayId) {

        return gatewayRepository.findById(gatewayId)
                .orElseThrow(() -> new EntityNotFoundException("게이트웨이 없음"))
                .getGatewayName();
    }

    @Override
    public List<Gateway> getGateways() {

        return gatewayRepository.findAll();
    }
}
