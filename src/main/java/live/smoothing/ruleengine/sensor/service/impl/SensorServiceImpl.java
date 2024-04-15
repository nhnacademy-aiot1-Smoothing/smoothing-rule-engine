package live.smoothing.ruleengine.sensor.service.impl;

import live.smoothing.ruleengine.sensor.dto.SensorRegisterRequest;
import live.smoothing.ruleengine.sensor.service.SensorService;
import live.smoothing.ruleengine.sensor.entity.Sensor;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    @Override
    public boolean isRegistered(SensorData sensorData) {

        return sensorRepository.findBySensorName(sensorData.getTopic()).isPresent();
    }

    @Override
    public List<Sensor> getSensors(Integer gatewayId) {

        return sensorRepository.findByGatewayId(gatewayId);
    }

    @Transactional
    @Override
    public void saveSensor(SensorRegisterRequest request) {

        sensorRepository.findBySensorName(request.getSensorName())
                .ifPresent(sensor -> {
                    throw new IllegalArgumentException("이미 등록된 센서입니다.");
                });

        Sensor sensor = Sensor.builder()
                .sensorName(request.getSensorName())
                .topic(request.getTopic())
                .gatewayId(request.getGatewayId())
                .build();
        sensorRepository.save(sensor);
    }
}
