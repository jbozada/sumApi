package com.example.sumapi.service.impl;

import com.example.sumapi.client.ConfigurationClient;
import com.example.sumapi.exception.ConfigurationNotFoundException;
import com.example.sumapi.service.ConfigurationService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

    private final Cache<String, Integer> cache;
    private final ConfigurationClient client;

    @Override
    public Integer getConfigurationValue(String key) {
        try {
            Integer value = client.getConfigurationValue(key);
            cache.put(key, value);
            return value;
        } catch (ConfigurationNotFoundException ex) {
            Integer cachedValue = cache.getIfPresent(key);
            if (cachedValue != null) {
                log.warn("Configuration for key '{}' not found in service, using cached value.", key);
                return cachedValue;
            } else {
                log.error("Configuration for key '{}' not found in service and cache is empty.", key);
                throw new ConfigurationNotFoundException("Configuration not found and cache is empty for key: " + key, ex);
            }
        }
    }
}
