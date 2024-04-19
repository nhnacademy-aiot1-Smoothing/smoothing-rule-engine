package live.smoothing.ruleengine.sensor.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MqttTopicRequest {

    private String topic;

}
