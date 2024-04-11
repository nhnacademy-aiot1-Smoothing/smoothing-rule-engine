package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.sensor.service.entity.MqttSensorData;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;

@RequiredArgsConstructor
public class MqttGatewayConsumer implements GatewayConsumer, MqttCallback {

    private final int connectionTimeout;
    private final int keepAliveInterval;
    private final boolean cleanSession;
    private final boolean automaticReconnect;

    private final RuleEngineManagement ruleEngineManagement;
    private final String gatewayUri;
    private final String gatewayName;
    private final int port;
    private final String clientId;
    private final String userName;
    private final String password;

    MqttClient client;

    @Override
    public String getGatewayUri() {
        return gatewayUri;
    }

    @Override
    public String getGatewayName() {
        return gatewayName;
    }

    @Override
    public void subscribe(String topic) throws Exception {
        client.subscribe(topic);
    }

    @Override
    public void unsubscribe(String topic) throws Exception {
        client.unsubscribe(topic);
    }

    @Override
    public void start() throws Exception {
        client = new MqttClient("tcp://" + gatewayUri + ":" + port, clientId);
        client.setCallback(this);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(automaticReconnect);
        options.setCleanSession(cleanSession);
        options.setConnectionTimeout(connectionTimeout);
        options.setKeepAliveInterval(keepAliveInterval);
        client.connect(options);
    }

    @Override
    public void stop() throws Exception {
        client.disconnect();
        client.close();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ruleEngineManagement.consume(new MqttSensorData(s, new String(mqttMessage.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
