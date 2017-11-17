package com.sprinthive.starter.health;

import com.sprinthive.starter.PropsService;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    PropsService propsService;
    @Autowired
    HealthCheckService healthCheckService;

    @PostConstruct
    private void init() {
        // wait 5 seconds for the app to start up and then run the health check,
        // we need to wait for the consumer to start listening
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribe(x -> heathCheck());
    }

    @RequestMapping(value = "/ping")
    private String ping() {
        return "OK";
    }

    @RequestMapping(value = "/health/check")
    private List<HeathCheckDto> heathCheck() {
        log.info("Running health check");
        List<HeathCheckDto> healthCheckList = new ArrayList<>();
        healthCheckList.add(healthCheckService.checkProps());
        healthCheckList.add(healthCheckService.checkMongo());
        healthCheckList.add(healthCheckService.checkRabbit());
        return healthCheckList;
    }
}
