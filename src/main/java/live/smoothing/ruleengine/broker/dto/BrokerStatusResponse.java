package live.smoothing.ruleengine.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class BrokerStatusResponse {
    private final Map<Integer, String> brokerStatus;
}
