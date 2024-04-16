package live.smoothing.ruleengine.sensor.repository;

import live.smoothing.ruleengine.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, String> {

    List<Sensor> findByBrokerBrokerId(Integer brokerId);
    Optional<Sensor> findBySensorName(String sensorName);
}
