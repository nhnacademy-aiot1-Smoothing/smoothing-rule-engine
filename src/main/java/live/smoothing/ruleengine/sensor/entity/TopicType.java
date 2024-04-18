package live.smoothing.ruleengine.sensor.entity;

import com.influxdb.annotations.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicType {

    @Column(name = "topic_type")
    private String topicType;
}
