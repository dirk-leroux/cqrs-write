package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    PropsService propsService;
    @Autowired
    HealthCheckService healthCheckService;

    @RequestMapping(value = "/ping")
    private String ping() {
        return "OK";
    }

    @RequestMapping(value = "/health/check")
    private List<HeathCheckDto> heathCheck() {
        log.info("Running health check");
        return healthCheckService.deepPing();
    }
}
