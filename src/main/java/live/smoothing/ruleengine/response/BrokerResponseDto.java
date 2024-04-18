package live.smoothing.ruleengine.response;

import live.smoothing.ruleengine.sensor.entity.Sensor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrokerResponseDto {

    @NonNull
    private Integer ip;

    @NonNull
    private String id;

    @NonNull
    private Integer port;

    private List<Sensor> sensors;
}
