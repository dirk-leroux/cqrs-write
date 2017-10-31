package com.sprinthive.starter;

import com.sprinthive.starter.redis.RedisService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisService getRedisService() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://redis:6379");
        RedissonClient client = Redisson.create(config);
        return new RedisService(client);
    }

}
