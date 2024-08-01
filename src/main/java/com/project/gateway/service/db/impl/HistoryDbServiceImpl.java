package com.project.gateway.service.db.impl;

import com.project.gateway.model.History;
import com.project.gateway.repository.HistoryRepository;
import com.project.gateway.service.db.HistoryDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryDbServiceImpl implements HistoryDbService {
    private final HistoryRepository historyRepository;
    private final DateTimeFormatter formatter;

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Override
    @Cacheable(cacheNames = "historyByBaseCurrencyOrderByTimestamp", key = "#baseCurrency")
    public History findFirstByBaseCurrencyOrderByTimestampDesc(String baseCurrency) {
        log.info("--------------Database called----------------");
        return historyRepository.findFirstByBaseCurrencyOrderByTimestampDesc(baseCurrency);
    }

    @Override
    @Cacheable(cacheNames = "allHistoriesByTimestampBetweenOrderByTimestampDesc", key = "{#from, #to}")
    public List<History> findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime from, LocalDateTime to) {
        log.info("--------------Database called----------------");
        return historyRepository.findAllByTimestampBetweenOrderByTimestampDesc(from, to);
    }
}
