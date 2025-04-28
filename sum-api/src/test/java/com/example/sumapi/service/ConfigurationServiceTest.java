package com.example.sumapi.service;

import com.example.sumapi.client.ConfigurationClient;
import com.example.sumapi.exception.ConfigurationNotFoundException;
import com.github.benmanes.caffeine.cache.Cache;
import com.example.sumapi.service.impl.ConfigurationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigurationServiceTest {

    @Mock
    private Cache<String, Integer> cache;

    @Mock
    private ConfigurationClient client;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConfigurationValue_success() {
        String key = "percentage";
        Integer value = 10;

        when(client.getConfigurationValue(key)).thenReturn(value);

        Integer result = configurationService.getConfigurationValue(key);

        assertEquals(value, result);
        verify(client, times(1)).getConfigurationValue(key);
        verify(cache, times(1)).put(key, value);
    }

    @Test
    void testGetConfigurationValue_failureWithCacheHit() {
        String key = "percentage";
        Integer cachedValue = 15;

        when(client.getConfigurationValue(key)).thenThrow(new ConfigurationNotFoundException("Not found"));
        when(cache.getIfPresent(key)).thenReturn(cachedValue);

        Integer result = configurationService.getConfigurationValue(key);

        assertEquals(cachedValue, result);
        verify(client, times(1)).getConfigurationValue(key);
        verify(cache, times(1)).getIfPresent(key);
    }

    @Test
    void testGetConfigurationValue_failureWithCacheMiss() {
        String key = "percentage";

        when(client.getConfigurationValue(key)).thenThrow(new ConfigurationNotFoundException("Not found"));
        when(cache.getIfPresent(key)).thenReturn(null);

        ConfigurationNotFoundException exception = assertThrows(ConfigurationNotFoundException.class,
                () -> configurationService.getConfigurationValue(key));

        assertTrue(exception.getMessage().contains("Configuration not found and cache is empty"));
        verify(client, times(1)).getConfigurationValue(key);
        verify(cache, times(1)).getIfPresent(key);
    }
}
