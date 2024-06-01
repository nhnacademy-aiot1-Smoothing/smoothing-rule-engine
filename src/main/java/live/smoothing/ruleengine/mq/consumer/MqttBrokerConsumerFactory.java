package live.smoothing.ruleengine.mq.consumer;

import java.util.UUID;

public class MqttBrokerConsumerFactory implements BrokerConsumerFactory {
    @Override
    public BrokerConsumer create(String brokerIp, int brokerPort, Integer brokerId, String protocolType) {

        return new MqttBrokerConsumer(
                brokerId,
                30,
                30,
                true,
                true,
                brokerIp,
                brokerPort,
                UUID.randomUUID() + brokerId.toString()
        );
    }
}
