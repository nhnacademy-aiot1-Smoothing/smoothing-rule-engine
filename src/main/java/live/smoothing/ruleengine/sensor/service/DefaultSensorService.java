package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.feign.adapter.DeviceAdapter;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("sensorService")
public class DefaultSensorService implements SensorService {
    private final DeviceAdapter deviceAdapter;

    @Override
    public List<BrokerResponseDto> getBrokerGenerateRequest() {
        return deviceAdapter.loadBrokerByBrokerId();
    }
}
