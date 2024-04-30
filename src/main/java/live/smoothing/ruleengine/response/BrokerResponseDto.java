package live.smoothing.ruleengine.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BrokerResponseDto {

    private Integer brokerId;

    private String brokerIp;

    private Integer brokerPort;

    private String protocolType;

    private Set<String> topics;
}
