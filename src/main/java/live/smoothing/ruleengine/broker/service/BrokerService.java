package live.smoothing.ruleengine.broker.service;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.entity.Broker;

import java.util.List;

public interface BrokerService {

    Broker addBroker(BrokerGenerateRequest request);

    String getBrokerName(Integer brokerId);

    List<Broker> getBrokers();
}
