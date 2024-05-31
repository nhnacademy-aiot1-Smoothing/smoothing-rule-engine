package live.smoothing.ruleengine.feign.config;

import feign.codec.ErrorDecoder;
import live.smoothing.ruleengine.feign.decoder.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

}
