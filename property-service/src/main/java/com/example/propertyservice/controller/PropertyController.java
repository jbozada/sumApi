package com.example.propertyservice.controller;

import com.example.propertyservice.dto.PropertyResponse;
import com.example.propertyservice.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing property-related API endpoints.
 */
@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    /**
     * Endpoint to retrieve a property by its key.
     *
     * @param key The key of the property.
     * @return The property value.
     */
    @GetMapping("/{key}")
    public ResponseEntity<PropertyResponse> getProperty(@PathVariable String key) {
        PropertyResponse response = propertyService.getPropertyValue(key);
        return ResponseEntity.ok(response);
    }
    
}
