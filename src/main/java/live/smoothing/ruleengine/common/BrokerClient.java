package live.smoothing.ruleengine.common;

import live.smoothing.ruleengine.response.BrokerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "broker-detail-service", path = "/api/broker")
public interface BrokerClient {

    @GetMapping("/search/loadBrokerByBrokerId")
    public Optional<BrokerResponseDto> loadBrokerByBrokerId(@RequestParam Integer brokerId);

}