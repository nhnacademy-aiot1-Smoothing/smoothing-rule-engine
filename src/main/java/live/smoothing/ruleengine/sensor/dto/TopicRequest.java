package live.smoothing.ruleengine.sensor.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicRequest {
    private Integer brokerId;
    private String topic;
}
