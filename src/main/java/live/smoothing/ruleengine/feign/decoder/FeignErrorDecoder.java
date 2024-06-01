package live.smoothing.ruleengine.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return new CommonException(HttpStatus.valueOf(response.status()), response.reason());
    }
}