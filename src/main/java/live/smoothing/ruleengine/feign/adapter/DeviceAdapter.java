package live.smoothing.ruleengine.feign.adapter;

import live.smoothing.ruleengine.feign.config.FeignConfig;
import live.smoothing.ruleengine.response.BrokerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "device-service", path = "/api/device",configuration = FeignConfig.class)
public interface DeviceAdapter {

    @GetMapping("/initialization")
    public List<BrokerResponseDto> loadBrokerByBrokerId();

}