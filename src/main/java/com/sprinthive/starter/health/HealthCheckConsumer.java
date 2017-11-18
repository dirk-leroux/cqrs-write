package com.sprinthive.starter.health;

import com.sprinthive.starter.events.AppEvent;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HealthCheckConsumer {

    private final HealthCheckService healthCheckService;

    public HealthCheckConsumer(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @PostConstruct
    private void init() {
        // wait 5 seconds for the app to start up and then run the health check,
        // we need to wait for the consumer to start listening
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(x -> healthCheckService.deepPing());
    }

    @StreamListener("healthCheckMessageConsumer")
    public void healthCheckMessageConsumer(AppEvent e) {
        log.info("Received {} eventId: {}",
                e.getType(),
                e.getEventId());
    }
}
