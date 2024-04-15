package live.smoothing.ruleengine.gateway.repository;

import live.smoothing.ruleengine.gateway.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, Integer> {

    boolean existsByGatewayIpOrGatewayName(String ip, String name);

}
