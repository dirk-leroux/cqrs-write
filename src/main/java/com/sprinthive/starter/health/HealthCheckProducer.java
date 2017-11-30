package com.sprinthive.starter.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public class HealthCheckProducer {

    private final MessageChannel healthCheckProducer;

    public HealthCheckProducer(MessageChannel healthCheckProducer) {
        this.healthCheckProducer = healthCheckProducer;
    }

    public void healthCheck(TestMessage msg) {
        log.debug("Sending health check message to rabbitmq messageId: {}", msg.getMessageId());
        healthCheckProducer.send(MessageBuilder.withPayload(msg).build());
    }
}
