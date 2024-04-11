package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.sensor.service.entity.SensorData;

public interface SensorService {
    boolean isRegistered(SensorData sensorData);
}
