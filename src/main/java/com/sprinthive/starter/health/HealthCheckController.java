package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    PropsService propsService;

    @Autowired
    HealthCheckService healthCheckService;

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

    @RequestMapping(value = "/health/rabbit")
    private String rabbit() {
        log.info("Sending a test message to rabbit");
        healthCheckService.sendMessage("Testing 123");
        return "Rabbit test message sent";
    }
}
