package live.smoothing.ruleengine.common;

import live.smoothing.ruleengine.response.BrokerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "device-service", path = "/api/device")
public interface BrokerClient {

    @GetMapping("/initialization")
    public List<BrokerResponseDto> loadBrokerByBrokerId();

}