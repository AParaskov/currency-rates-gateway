package com.project.gateway.service.db.impl;

import com.project.gateway.model.Statistics;
import com.project.gateway.repository.StatisticsRepository;
import com.project.gateway.service.db.StatisticsDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsDbServiceImpl implements StatisticsDbService {
    private final StatisticsRepository statisticsRepository;

    @Override
    public Statistics save(Statistics statistics) {
        return statisticsRepository.saveAndFlush(statistics);
    }

    @Override
    public Optional<Statistics> findByRequestId(String requestId) {
        return statisticsRepository.findByRequestId(requestId);
    }
}
