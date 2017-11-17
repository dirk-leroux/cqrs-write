package com.sprinthive.starter.health;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class HeathCheckDto {

    private String name;
    private String status;
    private List<String> errors;


}
