package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.common.BrokerClient;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("sensorService")
public class DefaultSensorService implements SensorService {
    private final BrokerClient brokerClient;

    @Override
    public List<BrokerResponseDto> getBrokerGenerateRequest() {
        return brokerClient.loadBrokerByBrokerId();
    }
}
