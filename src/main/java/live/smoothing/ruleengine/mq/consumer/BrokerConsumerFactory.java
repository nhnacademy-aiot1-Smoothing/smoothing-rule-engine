package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.protocoltype.entity.ProtocolType;

public interface BrokerConsumerFactory {

    BrokerConsumer create(String brokerIp,
                          int brokerPort,
                          String brokerName,
                          ProtocolType protocolType);


}
