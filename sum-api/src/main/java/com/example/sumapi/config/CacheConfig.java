package com.example.sumapi.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache configuration for application.
 */
@Configuration
public class CacheConfig {

    @Value("${cache.expiration-time}")
    private long expirationTime;

    @Bean
    public Cache<String, Integer> configurationCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(expirationTime, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }
}
