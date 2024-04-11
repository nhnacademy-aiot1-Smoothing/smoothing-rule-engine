package live.smoothing.ruleengine.mq.consumer;

public interface GatewayConsumerFactory {
    GatewayConsumer create(String gatewayIp, int gatewayPort, String gatewayName, String gatewayType);
}
