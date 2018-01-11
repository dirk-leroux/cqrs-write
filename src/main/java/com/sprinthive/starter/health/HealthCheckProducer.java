package com.sprinthive.starter.health;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HealthCheckProducer {

    private final MessageChannel healthCheckProducer;

    public HealthCheckProducer(MessageChannel healthCheckProducer) {
        this.healthCheckProducer = healthCheckProducer;
    }

    public void healthCheck(TestMessage msg) {
        log.debug("Sending health check to messaging with messageId: {}", msg.getMessageId());
        healthCheckProducer.send(MessageBuilder.withPayload(msg).build());
    }
}
