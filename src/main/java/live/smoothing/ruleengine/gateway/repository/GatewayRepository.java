package live.smoothing.ruleengine.gateway.repository;

import live.smoothing.ruleengine.gateway.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GatewayRepository extends JpaRepository<Gateway, Integer> {

    Optional<Gateway> findById(Integer gatewayId);

    boolean existsByGatewayIpOrGatewayName(String ip, String name);

}
