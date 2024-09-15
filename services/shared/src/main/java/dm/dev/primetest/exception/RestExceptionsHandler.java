package dm.dev.primetest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionsHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResp> catchAllErrors(Exception e) {
        log.error("Error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResp("Error happened, but all must be ok, we're working on it :) "));
    }
}
