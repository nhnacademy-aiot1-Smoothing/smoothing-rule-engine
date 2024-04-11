package live.smoothing.ruleengine.mq.consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttGatewayConsumer implements GatewayConsumer, MqttCallback {

    @Override
    public String getGatewayUrl() {
        return "";
    }

    @Override
    public void subscribe(String topic) {
    }

    @Override
    public void unsubscribe(String topic) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
