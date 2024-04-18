package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.broker.entity.ProtocolType;

public interface BrokerConsumerFactory {

    BrokerConsumer create(String brokerIp,
                          int brokerPort,
                          Integer brokerId,
                          ProtocolType protocolType);


}
