package com.sprinthive.starter.events;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class AppEventService {

    private final AppEventRepository appEventRepository;

    public AppEventService(AppEventRepository appEventRepository) {
        this.appEventRepository = appEventRepository;
    }

    public String createMongodbHealthCheckEvent() {
        String eventId = UUID.randomUUID().toString();
        appEventRepository.save(AppEvent.builder().eventId(eventId).type("MongodbHealthCheck").build());
        return eventId;
    }

    public AppEvent findById(String eventId) {
        log.debug("Find app event by id: {}", eventId);
        return appEventRepository.findByEventId(eventId);
    }
}
