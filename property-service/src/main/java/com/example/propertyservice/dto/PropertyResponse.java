package com.example.propertyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyResponse {
    private String name;
    private String value;
}
