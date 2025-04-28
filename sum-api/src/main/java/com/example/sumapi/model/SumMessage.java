package com.example.sumapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class SumMessage {
    private String request;
    private String response;
    private String endpoint;
    private LocalDateTime date;
    private Integer codResponse;
}
