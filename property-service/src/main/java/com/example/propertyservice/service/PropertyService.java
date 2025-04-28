package com.example.propertyservice.service;

import com.example.propertyservice.exception.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.propertyservice.dto.PropertyResponse;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${percentage.default-value}")
    private String defaultPercentage;

    @PostConstruct
    public void loadDefaultProperties() {
        if (redisTemplate.opsForValue().get("percentage") == null) {
            redisTemplate.opsForValue().set("percentage", defaultPercentage);
        }
    }

    public PropertyResponse getPropertyValue(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new PropertyNotFoundException("Property with key '" + key + "' not found.");
        }
        return new PropertyResponse(key, value);
    }
}
