package com.hiperautos.logservice.repository;

import com.hiperautos.logservice.model.LogAnalysisResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogAnalysisResponse, Long> {
}
