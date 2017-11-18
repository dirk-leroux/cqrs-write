package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import com.sprinthive.starter.events.AppEvent;
import com.sprinthive.starter.events.AppEventRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class HealthCheckService {

    private PropsService propsService;
    private AppEventRepository appEventRepository;
    private HealthCheckProducer healthCheckProducer;
    
    public HealthCheckService(PropsService propsService,
                              AppEventRepository appEventRepository,
                              HealthCheckProducer healthCheckProducer) {
        this.propsService = propsService;
        this.appEventRepository = appEventRepository;
        this.healthCheckProducer = healthCheckProducer;
    }

    public List<HeathCheckDto> deepPing() {
        List<HeathCheckDto> healthCheckList = new ArrayList<>();
        healthCheckList.add(checkProps());
        healthCheckList.add(checkMongo());
        healthCheckList.add(checkRabbit());
        return healthCheckList;
    }

    private HeathCheckDto checkProps() {
        HeathCheckDto.HeathCheckDtoBuilder builder = HeathCheckDto.builder().name("Property check");
        List<String> errors = new ArrayList<>();
        builder.errors(errors);
        try {
            log.debug("Checking the health of the props service");
            builder.status(propsService.heathCheck());
        } catch (Exception e) {
            builder.status("Failed");
            errors.add(e.getMessage());
        }
        return builder.build();
    }

    private HeathCheckDto checkMongo() {
        HeathCheckDto.HeathCheckDtoBuilder builder = HeathCheckDto.builder().name("Checking read and write to Mongodb");
        List<String> errors = new ArrayList<>();
        builder.errors(errors);

        String eventId = UUID.randomUUID().toString();
        appEventRepository.save(AppEvent.builder().eventId(eventId).type("MongoHealthCheck").build());

        AppEvent appEvent = appEventRepository.findByEventId(eventId);
        if (appEvent == null) {
            builder.status("Failed");
            errors.add("Could not find app event for eventId: " + eventId);
        } else {
            builder.status("OK");
        }

        return builder.build();
    }

    private HeathCheckDto checkRabbit() {
        HeathCheckDto.HeathCheckDtoBuilder builder = HeathCheckDto.builder().name("Send message to RabbitMQ");
        List<String> errors = new ArrayList<>();
        builder.errors(errors);

        try {
            String eventId = UUID.randomUUID().toString();
            healthCheckProducer.healthCheck(AppEvent.builder().eventId(eventId).type("RabbitHealthCheck").build());
            builder.status("OK");
        } catch (Exception e) {
            log.error("Rabbit health check failed", e);
            builder.status("Failed");
            errors.add(e.getMessage());
        }
        return builder.build();
    }
}
