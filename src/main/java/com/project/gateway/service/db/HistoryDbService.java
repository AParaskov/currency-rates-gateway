package com.project.gateway.service.db;

import com.project.gateway.model.History;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryDbService {
    History save(History history);
    History findFirstByBaseCurrencyOrderByTimestampDesc(String baseCurrency);
    List<History> findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime from, LocalDateTime to);

}
