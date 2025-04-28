package com.example.propertyservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<String> handlePropertyNotFound(PropertyNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
