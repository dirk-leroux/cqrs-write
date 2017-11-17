package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import com.sprinthive.starter.events.AppEvent;
import com.sprinthive.starter.events.AppEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class HealthCheckService {

    private PropsService propsService;
    private AppEventRepository appEventRepository;

    public HealthCheckService(PropsService propsService,
                              AppEventRepository appEventRepository) {
        this.propsService = propsService;
        this.appEventRepository = appEventRepository;
    }

    HeathCheckDto checkProps() {
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

    HeathCheckDto checkMongo() {
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

}
