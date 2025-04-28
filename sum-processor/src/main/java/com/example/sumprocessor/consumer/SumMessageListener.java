package com.example.sumprocessor.consumer;

import com.example.sumprocessor.entity.SumRecord;
import com.example.sumprocessor.service.SumRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SumMessageListener {

    private final SumRecordService sumRecordService;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic}")
    private String topic;

    @KafkaListener(topics = "${kafka.topic}", groupId = "sum-processor-group")
    public void listen(String message) {
        try {
            SumRecord record = objectMapper.readValue(message, SumRecord.class);
            sumRecordService.save(record);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
