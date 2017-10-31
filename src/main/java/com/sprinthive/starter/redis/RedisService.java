package com.sprinthive.starter.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import javax.annotation.PreDestroy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisService {

    private static final String TEST_VALUE_KEY = "spring-starter::testValue";
    private static final String TOKEN_KEY = "spring-starter::token";
    private static final String TOKEN_LOCK = "spring-starter::token-lock";

    private RedissonClient redisson = null;

    public RedisService(RedissonClient client) {
        redisson = client;
    }

    @PreDestroy
    public void destroy() {
        if (redisson != null) {
            redisson.shutdown();
        }
    }

    public void saveTestValue(String value) {
        RBucket bucket = redisson.getBucket(TEST_VALUE_KEY, StringCodec.INSTANCE);
        bucket.set(value);
    }

    public void saveTestValue(String value, int expirySecs) {
        RBucket bucket = redisson.getBucket(TEST_VALUE_KEY);
        bucket.set(value, expirySecs, TimeUnit.SECONDS);
    }

    public String readTestValue() {
        RBucket bucket = redisson.getBucket(TEST_VALUE_KEY, StringCodec.INSTANCE);
        Object val = bucket.get();
        if (val != null) {
            return (String) val;
        } else {
            throw new ValueNotFoundException("Object not found with key "+ TEST_VALUE_KEY);
        }
    }

    private void saveToken(TokenDto token) {
        RBucket<TokenDto> bucket = redisson.getBucket(TOKEN_KEY);
        bucket.set(token, 5, TimeUnit.SECONDS);
    }

    TokenDto getToken() {
        RBucket<TokenDto> bucket = redisson.getBucket(TOKEN_KEY);
        TokenDto val = bucket.get();
        if (val != null) {
            return val;
        } else {
            return createToken();
        }
    }

    private TokenDto createToken() {
        TokenDto token = null;
        RLock lock = redisson.getFairLock(TOKEN_LOCK);
        try {
            if (!lock.tryLock()) {
                log.debug("Could not get a lock going to try again in 1sec");
                Thread.sleep(1000);
                return getToken();
            } else {
                log.debug("Got the lock going to create a token");
                Thread.sleep(4000);
                token = TokenDto.builder().token(UUID.randomUUID().toString()).build();
                saveToken(token);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return token;
    }

}
