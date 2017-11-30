package com.sprinthive.starter.events;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppEventRepository extends MongoRepository<AppEvent, String> {

    AppEvent findByEventId(String eventId);
}
