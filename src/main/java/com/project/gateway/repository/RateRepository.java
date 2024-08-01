package com.project.gateway.repository;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByHistoryId(History historyId);
}
