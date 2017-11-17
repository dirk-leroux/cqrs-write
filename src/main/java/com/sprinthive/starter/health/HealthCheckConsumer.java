package com.sprinthive.starter.health;

import com.sprinthive.starter.events.AppEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
public class HealthCheckConsumer {

    @StreamListener("healthCheckMessageConsumer")
    public void healthCheckMessageConsumer(AppEvent e) {
        log.info("Received {} eventId: {}",
                e.getType(),
                e.getEventId());
    }
}
