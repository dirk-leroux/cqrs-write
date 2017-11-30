package com.sprinthive.starter.events;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AppEventDto {

    private String eventId;
    private String type;
    private LocalDateTime dateCreated;
}
