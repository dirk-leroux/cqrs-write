package com.sprinthive.starter.redis;

public class ValueNotFoundException extends RuntimeException {

    public ValueNotFoundException(String message) {
        super(message);
    }
}
