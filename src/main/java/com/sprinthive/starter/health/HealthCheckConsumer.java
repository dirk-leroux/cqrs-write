package com.sprinthive.starter.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
public class HealthCheckConsumer {

    @StreamListener("healthCheckMessageConsumer")
    public void healthCheckMessageConsumer(TestMessage msg) {
        log.info("Received {} eventId: {}",
                msg.getMessageId(),
                msg.getName());
    }
}
