package live.smoothing.ruleengine.sensor.service;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.response.BrokerResponseDto;

import java.util.List;

public interface SensorService {
    List<BrokerResponseDto> getBrokerGenerateRequest();
}
