package com.sprinthive.starter.events;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Value
@Builder
@Document(collection = "AppEventStream")
public class AppEvent<T> {

    Instant dateCreated = Instant.now();
    String eventId;
    String type;
    T payload;
}
