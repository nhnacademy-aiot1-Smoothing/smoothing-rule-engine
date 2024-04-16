package live.smoothing.ruleengine.mq.producer;

public interface MessageQueueProducer {

    void sendMessage(Object message);
    void connect();
    void disconnect();
}
