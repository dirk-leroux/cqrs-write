package com.sprinthive.starter.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
@RestController
public class AppEventController {

    @Autowired
    AppEventService appEventService;

    @RequestMapping("/event/test")
    public AppEventDto insert() {
        String eventId = appEventService.createMongodbHealthCheckEvent();
        return AppEventDto.builder().eventId(eventId).build();
    }

    @RequestMapping("/event/find/{eventId}")
    public AppEventDto search(@PathVariable String eventId) {
        AppEvent found = appEventService.findById(eventId);

        log.info(ZoneOffset.getAvailableZoneIds().toString());
        return AppEventDto.builder()
                .eventId(found.getEventId())
                .type(found.getType())
                .dateCreated(LocalDateTime.ofInstant(found.getDateCreated(), ZoneId.of("Africa/Johannesburg")))
                .build();
    }
}
