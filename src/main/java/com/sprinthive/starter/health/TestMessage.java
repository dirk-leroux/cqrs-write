package com.sprinthive.starter.health;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TestMessage {

    private String messageId;
    private String name;
}
