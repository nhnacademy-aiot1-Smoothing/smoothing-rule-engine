package live.smoothing.ruleengine.topic.entity;

import live.smoothing.ruleengine.sensor.entity.Sensor;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@Table(name = "topics")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic {

    @Id
    private String topic;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @OneToOne
    @JoinColumn(name = "topic_type")
    private TopicType topicType;
}
