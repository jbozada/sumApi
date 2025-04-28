package com.example.sumapi.client;

import com.example.sumapi.exception.ConfigurationNotFoundException;
import com.example.sumapi.model.ConfigurationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ConfigurationClient {

    private final WebClient webClient;

    @Value("${configuration.api.path}")
    private String configurationApiPath;

    public Integer getConfigurationValue(String key) {
        try {
            ConfigurationResponse response = webClient.get()
                    .uri(configurationApiPath, key)
                    .retrieve()
                    .bodyToMono(ConfigurationResponse.class)
                    .block();

            if (response == null || response.getValue() == null) {
                throw new ConfigurationNotFoundException("No configuration found for key: " + key);
            }

            return Integer.parseInt(response.getValue());
        } catch (Exception e) {
            throw new ConfigurationNotFoundException("Error occurred while fetching configuration for key: " + key, e);
        }
    }
}
