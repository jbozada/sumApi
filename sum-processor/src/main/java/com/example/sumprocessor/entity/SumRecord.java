package com.example.sumprocessor.entity;

import jakarta.persistence.*;
        import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "sum_records")
@Data
public class SumRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String request;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(nullable = false, length = 255)
    private String endpoint;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "cod_response", nullable = false)
    private Integer codResponse;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
