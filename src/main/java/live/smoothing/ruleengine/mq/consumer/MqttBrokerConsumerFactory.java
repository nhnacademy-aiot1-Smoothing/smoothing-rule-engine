package live.smoothing.ruleengine.mq.consumer;

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
                brokerId.toString()
        );
    }
}
