package com.example.sumapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for returning sum history records.
 */
@Data
@Builder
@AllArgsConstructor
public class SumRecordResponse {

    private String request;
    private String response;
    private String endpoint;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private Integer codResponse;
}
