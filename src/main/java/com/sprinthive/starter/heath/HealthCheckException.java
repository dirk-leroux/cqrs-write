package com.sprinthive.starter.heath;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class HealthCheckException extends RuntimeException {

    public HealthCheckException(String message) {
        super(message);
    }
}
