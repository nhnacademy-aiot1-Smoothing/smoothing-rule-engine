package live.smoothing.ruleengine.mq.consumer;

public interface BrokerConsumerFactory {

    BrokerConsumer create(String brokerIp,
                          int brokerPort,
                          Integer brokerId,
                          String protocolType);


}
