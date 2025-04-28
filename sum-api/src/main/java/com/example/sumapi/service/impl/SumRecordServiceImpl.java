package com.example.sumapi.service.impl;

import com.example.sumapi.dto.SumRecordResponse;
import com.example.sumapi.entity.SumRecord;
import com.example.sumapi.repository.SumRecordRepository;
import com.example.sumapi.service.SumRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service implementation for managing Sum Records.
 */
@Service
@RequiredArgsConstructor
public class SumRecordServiceImpl implements SumRecordService {

    private final SumRecordRepository sumRecordRepository;

    @Override
    public Page<SumRecordResponse> getAllSumRecords(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<SumRecord> records = sumRecordRepository.findAll(sortedPageable);

        return records.map(record -> SumRecordResponse.builder()
                .request(record.getRequest())
                .response(record.getResponse())
                .endpoint(record.getEndpoint())
                .date(record.getDate())
                .codResponse(record.getCodResponse())
                .build()
        );
    }
}
