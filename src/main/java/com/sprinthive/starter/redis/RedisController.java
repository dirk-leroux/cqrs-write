package com.sprinthive.starter.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/redis/hello")
    private String hello() {
        String testValue = "Sugar is yuck";
        log.debug("Storing " + testValue + " into redis");
        redisService.saveTestValue(testValue);
        String ans = redisService.readTestValue();
        log.debug("Reading testValue from redis: " + ans);
        return "Test Value Saved " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/redis/hello-with-timeout")
    private String helloWithTimeout() {
        String testValue = "Jon was here";
        log.debug("Storing " + testValue + " into redis with timeout");
        redisService.saveTestValue(testValue, 10);
        String ans = redisService.readTestValue();
        log.debug("Reading testValue from redis: " + ans);
        return "Test Value Saved but will self destruct in 10 seconds " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/redis/state")
    private String commonState() {
        log.debug("Trying to get a token");
        String token = redisService.getToken().token;
        log.debug("Got a token "+ token);
        return token;
    }
}
