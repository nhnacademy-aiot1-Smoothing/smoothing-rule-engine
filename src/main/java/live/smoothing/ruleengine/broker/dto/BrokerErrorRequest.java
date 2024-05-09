package live.smoothing.ruleengine.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BrokerErrorRequest {

    private Integer brokerId;
    private String brokerErrorType;
    private String createdAt;

    @Builder
    public BrokerErrorRequest(Integer brokerId, String brokerErrorType) {
        this.brokerId = brokerId;
        this.brokerErrorType = brokerErrorType;
        this.createdAt = LocalDateTime.now().toString();
    }
}
