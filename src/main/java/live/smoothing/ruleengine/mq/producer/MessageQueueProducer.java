package live.smoothing.ruleengine.mq.producer;

public interface MessageQueueProducer {

    void send(Object message);
    void connect();
    void disconnect();
}
