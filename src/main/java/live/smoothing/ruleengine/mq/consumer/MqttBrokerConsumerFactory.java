package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.broker.entity.ProtocolType;

public class MqttBrokerConsumerFactory implements BrokerConsumerFactory {

    @Override
    public BrokerConsumer create(String brokerIp, int brokerPort, String brokerName, ProtocolType protocolType) {

        return null;
    }
}
