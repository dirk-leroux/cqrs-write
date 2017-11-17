package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
    private List<HeathCheckDto> heathCheck() {
        log.info("Health check");
        List<HeathCheckDto> healthCheckList = new ArrayList<>();
        healthCheckList.add(healthCheckService.checkProps());
        healthCheckList.add(healthCheckService.checkMongo());
        return healthCheckList;
    }
}
