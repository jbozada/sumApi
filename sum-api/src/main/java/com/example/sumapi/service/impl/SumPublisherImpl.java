package com.example.sumapi.service.impl;

import com.example.sumapi.model.SumMessage;
import com.example.sumapi.service.SumPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SumPublisherImpl implements SumPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic}")
    private String topic;

    @Override
    public void send(SumMessage message) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            log.error("Unexpected error when sending message to Kafka: {}", e.getMessage(), e);
        }
    }
}
