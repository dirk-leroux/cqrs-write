package com.sprinthive.starter.cqrs.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sprinthive.starter.PropsService;
import com.sprinthive.starter.health.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class WriteController {

    @Autowired
    PropsService propsService;

    @Autowired
    WriteProducer writeProducer;

    @PostMapping(value = "/cqrs/write/v1/fact/{entityKey}/{entityId}/{action}")
    private String recordFact(@PathVariable String entityKey,
                              @PathVariable String entityId,
                              @PathVariable String action,
                              @RequestBody Map payload) throws JsonProcessingException {
        writeProducer.recordFact(entityKey, entityId, action, payload);
        return "Test message sent";
    }
}
