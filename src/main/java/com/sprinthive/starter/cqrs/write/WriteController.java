package com.sprinthive.starter.cqrs.write;

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

    @PostConstruct
    private void init() {
      log.info("Health check "+ heathCheck() );
    }

    @RequestMapping(value = "/ping")
    private String ping() {
        return "OK";
    }

    @RequestMapping(value = "/health/check")
    private String heathCheck() {
        log.info("Health check");
        return propsService.heathCheck();
    }

    @PostMapping(value = "/cqrs/write/v1/fact/{entityKey}/{entityId}/{action}")
    private String recordFact(@PathVariable String entityKey,
                              @PathVariable String entityId,
                              @PathVariable String action,
                              @RequestBody Map payload) {
        writeProducer.recordFact(entityKey, entityId, action, payload);
        return "Test message sent";
    }
}
