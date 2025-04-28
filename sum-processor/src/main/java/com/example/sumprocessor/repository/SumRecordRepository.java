package com.example.sumprocessor.repository;

import com.example.sumprocessor.entity.SumRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SumRecordRepository extends JpaRepository<SumRecord, Long> {
}
