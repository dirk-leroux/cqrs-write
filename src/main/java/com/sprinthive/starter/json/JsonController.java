package com.sprinthive.starter.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class JsonController {

    @RequestMapping("/json/example1")
    public ExampleDto example1() {
        ExampleDto dto = ExampleDto.builder()
                .id(1505140851471L)
                .name("Testing json mapping")
                .dateCreated(LocalDateTime.now())
                .dateOfBirth(LocalDate.of(2017, 9, 11))
                .build();
        return dto;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/json/example2")
    public ExampleDto example2(@RequestBody ExampleDto dto) {
        return dto;
    }
}
