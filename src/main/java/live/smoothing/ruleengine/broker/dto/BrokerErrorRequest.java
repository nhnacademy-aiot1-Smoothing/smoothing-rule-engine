package live.smoothing.ruleengine.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BrokerErrorRequest {

    private Integer brokerId;
    private String brokerErrorType;
    private LocalDateTime createdAt;
}
