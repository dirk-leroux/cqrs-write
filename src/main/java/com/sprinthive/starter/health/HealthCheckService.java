package com.sprinthive.starter.health;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class HealthCheckService {
    private final HealthCheckProducer healthCheckProducer;

    public HealthCheckService(HealthCheckProducer healthCheckProducer) {
        this.healthCheckProducer = healthCheckProducer;
    }

    public void sendMessage(String name) {
        log.debug("Sending health check message {}", name);
        String messageId = UUID.randomUUID().toString();
        TestMessage testMessage = TestMessage.builder()
                .messageId(messageId)
                .name(name)
                .build();
        healthCheckProducer.healthCheck(testMessage);
    }
}
