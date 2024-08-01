package com.project.gateway.service.db;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;

import java.util.List;

public interface RateDbService {
    Rate save(Rate rate);
    List<Rate> findAllByHistoryId(History historyId);
}
