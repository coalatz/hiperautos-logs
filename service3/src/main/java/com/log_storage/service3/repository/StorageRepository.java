package com.log_storage.service3.repository;

import com.log_storage.service3.model.LogAnalysisResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<LogAnalysisResponse, Long> {
}
