package com.example.sumapi.service;

import com.example.sumapi.dto.SumRecordResponse;
import com.example.sumapi.entity.SumRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SumRecordService {
    Page<SumRecordResponse> getAllSumRecords(Pageable pageable);
}
