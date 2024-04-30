package live.smoothing.ruleengine.mq.producer;

import live.smoothing.ruleengine.broker.dto.BrokerErrorRequest;
import live.smoothing.ruleengine.sensor.dto.SensorErrorRequest;

public interface ErrorProducer {
    void sendBrokerError(BrokerErrorRequest brokerErrorRequest);

    void sendSensorError(SensorErrorRequest sensorErrorRequest);
}
