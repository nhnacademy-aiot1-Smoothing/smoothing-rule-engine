package live.smoothing.ruleengine.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SensorResponseDto {

    @NonNull
    private Integer id;

    private List<TopicResponseDto> topics;
}
