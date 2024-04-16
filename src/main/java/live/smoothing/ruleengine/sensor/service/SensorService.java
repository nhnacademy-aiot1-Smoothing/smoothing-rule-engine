package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.sensor.dto.SensorRegisterRequest;
import live.smoothing.ruleengine.sensor.entity.Sensor;
import live.smoothing.ruleengine.sensor.entity.SensorData;

import java.util.List;

public interface SensorService {

    boolean isRegistered(SensorData sensorData);
    List<Sensor> getSensors(Integer brokerId);
    void saveSensor(SensorRegisterRequest request);
}
