package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;
import org.springframework.beans.factory.annotation.Autowired;

public class MqttBrokerConsumerFactory implements BrokerConsumerFactory {
    @Override
    public BrokerConsumer create(String brokerIp, int brokerPort, Integer brokerId, String protocolType) {

        return new MqttBrokerConsumer(
                30,
                30,
                true,
                true,
                brokerIp,
                brokerId.toString(),
                brokerPort,
                brokerId.toString()
        );
    }
}
