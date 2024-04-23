package live.smoothing.ruleengine.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrokerResponseDto {

    private String ip;

    private Integer id;

    private Integer port;

    private String protocolType;

    private List<String> topics;
}
