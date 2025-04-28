package com.example.sumprocessor.service.impl;

import com.example.sumprocessor.entity.SumRecord;
import com.example.sumprocessor.repository.SumRecordRepository;
import com.example.sumprocessor.service.SumRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SumRecordServiceImpl implements SumRecordService {

    private final SumRecordRepository repository;

    @Override
    public void save(SumRecord record) {
        repository.save(record);
    }
}
