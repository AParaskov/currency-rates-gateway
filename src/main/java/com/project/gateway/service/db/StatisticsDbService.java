package com.project.gateway.service.db;

import com.project.gateway.model.Statistics;

import java.util.Optional;

public interface StatisticsDbService {
    Statistics save(Statistics statistics);
    Optional<Statistics> findByRequestId(String requestId);
}
