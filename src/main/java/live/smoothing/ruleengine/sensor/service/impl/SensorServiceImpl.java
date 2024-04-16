package live.smoothing.ruleengine.sensor.service.impl;

import live.smoothing.ruleengine.broker.repository.BrokerRepository;
import live.smoothing.ruleengine.sensor.dto.SensorRegisterRequest;
import live.smoothing.ruleengine.sensor.service.SensorService;
import live.smoothing.ruleengine.sensor.entity.Sensor;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import live.smoothing.ruleengine.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final BrokerRepository brokerRepository;

    @Override
    public boolean isRegistered(String sensorName) {

        return sensorRepository
                .findBySensorName(sensorName)
                .isPresent();
    }

    @Override
    public List<Sensor> getSensors(Integer brokerId) {

        return sensorRepository.findByBrokerBrokerId(brokerId);
    }

    @Override
    @Transactional
    public Sensor saveSensor(SensorRegisterRequest request) {

        sensorRepository.findBySensorName(request.getSensorName())
                .ifPresent(sensor -> {
                    throw new IllegalArgumentException("이미 등록된 센서입니다.");
                });

        Sensor sensor = Sensor.builder()
                .sensorName(request.getSensorName())
                .broker(brokerRepository.getReferenceById(request.getBrokerId()))
                .sensorRegisteredAt(LocalDateTime.now())
                .sensorType(request.getSensorType())
                .build();
        return sensorRepository.save(sensor);
    }
}
