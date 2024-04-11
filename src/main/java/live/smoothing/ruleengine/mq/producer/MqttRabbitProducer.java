package live.smoothing.ruleengine.mq.producer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MqttRabbitProducer implements MessageQueueProducer {

    @Override
    public void sendMessage(Object message) {

    }

    @Override
    public void connect() {}

    @Override
    public void disconnect() {}
}
