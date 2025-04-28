package com.example.sumapi.service;

import com.example.sumapi.dto.SumRequest;
import com.example.sumapi.dto.SumResponse;

public interface SumService {
    SumResponse calculateAndSend(SumRequest request);
}
