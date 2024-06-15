package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.common.exception.CommonException;
import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.sensor.entity.MqttSensorData;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Slf4j
public class MqttBrokerConsumer implements BrokerConsumer, MqttCallback {

    private final static String BROKER_TYPE = "MQTT";

    private final int brokerId;
    private final int connectionTimeout;
    private final int keepAliveInterval;
    private final boolean cleanSession;
    private final boolean automaticReconnect;
    private RuleEngineManagement ruleEngineManagement;
    private final String brokerUri;
    private final int port;
    private final String clientId;
    private boolean isRunning = false;

    private MqttClient client;

    public MqttBrokerConsumer(int brokerId, int connectionTimeout, int keepAliveInterval, boolean cleanSession, boolean automaticReconnect, String brokerUri, int port, String clientId) {
        this.brokerId = brokerId;
        this.connectionTimeout = connectionTimeout;
        this.keepAliveInterval = keepAliveInterval;
        this.cleanSession = cleanSession;
        this.automaticReconnect = automaticReconnect;
        this.brokerUri = brokerUri;
        this.port = port;
        this.clientId = clientId;

        try {
            client = new MqttClient("tcp://" + brokerUri + ":" + port, clientId,
                    new MqttDefaultFilePersistence("./target/trash"));
        } catch (MqttException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "MQTT Client 생성 실패");
        }
        client.setCallback(this);
    }

    @Override
    public String getBrokerUri() {

        return brokerUri;
    }

    @Override
    public int getBrokerId() {
        return brokerId;
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

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(automaticReconnect);
        options.setCleanSession(cleanSession);
        options.setConnectionTimeout(connectionTimeout);
        options.setKeepAliveInterval(keepAliveInterval);
        client.connect(options);
        isRunning = true;
        log.debug("브로커 연결 성공 : {}", brokerUri);
    }

    @Override
    public void stop() throws Exception {

        if (client.isConnected()) {
            client.disconnect();
        }
        client.close(true);
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public BrokerConsumer copy() {
        return new MqttBrokerConsumer(brokerId, connectionTimeout, keepAliveInterval, cleanSession, automaticReconnect, brokerUri, port, clientId);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        ruleEngineManagement.sendBrokerError(BrokerErrorRequest.builder()
                .brokerId(brokerId)
                .brokerErrorType(throwable.getMessage())
                .build());
        try {
            stop();
        } catch (Exception e) {
            log.error("stop error", e);
        }
        isRunning = false;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        MqttSensorData data = new MqttSensorData(topic, new String(mqttMessage.getPayload()), brokerId, BROKER_TYPE);
        log.debug("messageArrived : {}", data);
        ruleEngineManagement.consume(data);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
