package live.smoothing.ruleengine.broker.repository;

import live.smoothing.ruleengine.broker.entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository extends JpaRepository<Broker, Integer> {

    boolean existsByBrokerIpOrBrokerName(String ip, String name);

}
