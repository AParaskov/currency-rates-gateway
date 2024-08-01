package com.project.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.gateway.model.dto.json.request.CurrentCurrencyRateRequest;
import com.project.gateway.model.dto.json.request.HistoryCurrencyRateRequest;
import com.project.gateway.model.dto.json.response.CurrentCurrencyRateResponse;
import com.project.gateway.model.dto.json.response.HistoryCurrencyRateResponse;
import com.project.gateway.model.dto.xml.request.CommandCurrencyRateRequest;
import com.project.gateway.model.dto.xml.response.CommandCurrencyRateResponse;

public interface StatisticsService {
    void collectStatistics(String serviceName, String requestId, Long time, String clientId) throws JsonProcessingException;

    CurrentCurrencyRateResponse getCurrentCurrencyRate(CurrentCurrencyRateRequest request);

    HistoryCurrencyRateResponse getHistoryCurrencyRate(HistoryCurrencyRateRequest request);

    CommandCurrencyRateResponse getCurrencyRateByCommand(String command, CommandCurrencyRateRequest request);

    boolean isStatisticsPresent(String requestId);
}
