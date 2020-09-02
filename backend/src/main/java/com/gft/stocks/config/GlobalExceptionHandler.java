package com.gft.stocks.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final String handleAllExceptions(Exception ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(),
                ex.getMessage());

        return exceptionResponse.toString();
    }

    @Data
    @AllArgsConstructor
    private class ExceptionResponse {
        private Instant timestamp;
        private String message;
    }
}
