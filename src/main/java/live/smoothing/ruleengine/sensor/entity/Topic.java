package live.smoothing.ruleengine.sensor.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic {

    private String topic;
    private Sensor sensor;
    private TopicType topicType;
}
