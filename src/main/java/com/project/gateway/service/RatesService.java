package com.project.gateway.service;

import com.project.gateway.model.dto.LatestCurrencyRateResponse;

public interface RatesService {
    LatestCurrencyRateResponse collectRates();

    void persistRates();
}
