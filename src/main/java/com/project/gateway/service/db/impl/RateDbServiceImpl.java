package com.project.gateway.service.db.impl;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import com.project.gateway.repository.RateRepository;
import com.project.gateway.service.db.RateDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateDbServiceImpl implements RateDbService {
    private final RateRepository rateRepository;

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Cacheable(cacheNames = "allRatesByHistoryId", key = "#historyId.id")
    @Override
    public List<Rate> findAllByHistoryId(History historyId) {
        return rateRepository.findAllByHistoryId(historyId);
    }
}
