package com.project.gateway.repository;

import com.project.gateway.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    History findFirstByBaseCurrencyOrderByTimestampDesc(String baseCurrency);
    List<History> findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime from, LocalDateTime to);
}