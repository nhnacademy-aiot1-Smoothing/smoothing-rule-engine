package live.smoothing.ruleengine.mq.consumer;

public class MqttBrokerConsumerFactory implements BrokerConsumerFactory {

    @Override
    public BrokerConsumer create(String brokerIp, int brokerPort, Integer brokerId, String protocolType) {

        return new MqttBrokerConsumer(
                30,
                30,
                true,
                true,
                null,
                brokerIp,
                brokerId.toString(),
                brokerPort,
                brokerId.toString()
        );
    }
}
