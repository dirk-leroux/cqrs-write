package com.sprinthive.starter;

import com.sprinthive.starter.health.HealthCheckConsumer;
import com.sprinthive.starter.health.HealthCheckProducer;
import com.sprinthive.starter.health.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthCheckConfig {

    @Autowired
    MessagingConfig.MessageChannels messageChannels;

    @Bean
    HealthCheckConsumer healthCheckConsumer() {
        return new HealthCheckConsumer();
    }

    @Bean
    HealthCheckProducer healthCheckProducer() {
        return new HealthCheckProducer(messageChannels.healthCheckMessageProducer());
    }

    @Bean
    public HealthCheckService healthCheckService(HealthCheckProducer healthCheckProducer) {
        return new HealthCheckService(healthCheckProducer);
    }
}
