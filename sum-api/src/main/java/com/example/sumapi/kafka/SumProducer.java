package com.example.sumapi.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SumProducer {

    private static final String TOPIC = "sum-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendToKafka(int number1, int number2, int result) {
        String message = number1 + "," + number2 + "," + result;
        kafkaTemplate.send(TOPIC, message);
    }
}