package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.sensor.entity.MqttSensorData;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

@Slf4j
@RequiredArgsConstructor
public class MqttBrokerConsumer implements BrokerConsumer, MqttCallback {

    private final int connectionTimeout;
    private final int keepAliveInterval;
    private final boolean cleanSession;
    private final boolean automaticReconnect;
    private RuleEngineManagement ruleEngineManagement;
    private final String brokerUri;
    private final String brokerName;
    private final int port;
    private final String clientId;

    MqttClient client;

    @Override
    public String getBrokerUri() {

        return brokerUri;
    }

    @Override
    public String getBrokerName() {

        return brokerName;
    }

    @Override
    public void setRuleEngineManagement(RuleEngineManagement ruleEngineManagement) {
        this.ruleEngineManagement = ruleEngineManagement;
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

        client = new MqttClient("tcp://" + brokerUri + ":" + port, clientId,
                new MqttDefaultFilePersistence("./target/trash"));
        client.setCallback(this);
        MqttConnectOptions options = new MqttConnectOptions();
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
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        MqttSensorData data = new MqttSensorData(topic, new String(mqttMessage.getPayload()));
        log.error("messageArrived : {}", data);
        ruleEngineManagement.consume(data);

//        ruleEngineManagement.consume(new MqttSensorData(topic, new String(mqttMessage.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
