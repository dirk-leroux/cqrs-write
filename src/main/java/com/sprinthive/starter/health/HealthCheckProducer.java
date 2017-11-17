package com.sprinthive.starter.health;

import com.sprinthive.starter.events.AppEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public class HealthCheckProducer {

    private final MessageChannel healthCheckProducer;

    public HealthCheckProducer(MessageChannel healthCheckProducer) {
        this.healthCheckProducer = healthCheckProducer;
    }

    public void healthCheck(AppEvent e) {
        log.debug("Sending health check message to rabbitmq eventId: {}", e.getEventId());
        healthCheckProducer.send(MessageBuilder.withPayload(e).build());
    }
}
