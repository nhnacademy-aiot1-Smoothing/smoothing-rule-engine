package live.smoothing.ruleengine.broker.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrokerDeleteRequest {
    private Integer brokerId;

}
