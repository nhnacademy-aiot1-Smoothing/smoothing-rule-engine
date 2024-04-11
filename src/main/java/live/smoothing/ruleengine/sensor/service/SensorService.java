package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.sensor.service.entity.Sensor;
import live.smoothing.ruleengine.sensor.service.entity.SensorData;

import java.util.List;

public interface SensorService {

    boolean isRegistered(SensorData sensorData);

    List<Sensor> getSensors();
}
