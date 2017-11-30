package com.sprinthive.starter;

import com.sprinthive.starter.events.AppEventRepository;
import com.sprinthive.starter.events.AppEventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppEventConfig {

    @Bean
    public AppEventService appEventService(AppEventRepository appEventRepository) {
        return new AppEventService(appEventRepository);
    }
}
