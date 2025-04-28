package com.example.sumapi.repository;

import com.example.sumapi.entity.SumRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SumRecordRepository extends JpaRepository<SumRecord, Long> {
}
