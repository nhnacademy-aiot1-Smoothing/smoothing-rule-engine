package live.smoothing.ruleengine.sensor.service.impl;

import live.smoothing.ruleengine.sensor.service.SensorService;
import live.smoothing.ruleengine.sensor.service.entity.Sensor;
import live.smoothing.ruleengine.sensor.service.entity.SensorData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    @Override
    public boolean isRegistered(SensorData sensorData) {

        return false;
    }

    @Override
    public List<Sensor> getSensors() {

        return List.of();
    }
}
