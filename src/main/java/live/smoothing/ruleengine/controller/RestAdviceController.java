package live.smoothing.ruleengine.controller;

import live.smoothing.common.dto.ErrorResponse;
import live.smoothing.common.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestAdviceController {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }
}
