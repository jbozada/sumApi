package com.example.sumapi.service.impl;

import com.example.sumapi.dto.SumRequest;
import com.example.sumapi.dto.SumResponse;
import com.example.sumapi.exception.InvalidInputException;
import com.example.sumapi.service.ConfigurationService;
import com.example.sumapi.service.SumPublisher;
import com.example.sumapi.service.SumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class SumServiceImpl implements SumService {

    private final SumPublisher publisher;
    private final ConfigurationService configurationService;

    @Override
    public SumResponse calculateAndSend(SumRequest request) {
        if (request.getNumber1() == null || request.getNumber2() == null) {
            throw new InvalidInputException("Both numbers are required.");
        }

        Integer percentage = configurationService.getConfigurationValue("percentage");

        int total = request.getNumber1() + request.getNumber2();
        BigDecimal result = new BigDecimal(total * percentage)
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        return new SumResponse(result.doubleValue());
    }
}
