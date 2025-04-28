package com.example.sumapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${configuration.service.url}")
    private String configurationApiBaseUrl;

    @Bean
    public WebClient configurationWebClient() {
        return WebClient.builder()
                .baseUrl(configurationApiBaseUrl)
                .build();
    }
}
