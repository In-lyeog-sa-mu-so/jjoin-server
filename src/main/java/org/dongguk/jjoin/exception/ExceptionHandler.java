package org.dongguk.jjoin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        e.printStackTrace();
        return e.getMessage();
    }
}
