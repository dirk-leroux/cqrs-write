package com.sprinthive.starter.cqrs.write;

import java.util.Map;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WritePayload {

    private String entityKey;
    private String entityId;
    private String action;
    private Map payload;
}
