package com.project.gateway.scheduler;

import com.project.gateway.service.RatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LatestCurrencyRatesScheduler {
    private final RatesService ratesService;

    @Scheduled(fixedRate = 3600000)
    public void scheduleLatestCurrencyRatesRetrieval() {
        ratesService.persistRates();
    }
}
