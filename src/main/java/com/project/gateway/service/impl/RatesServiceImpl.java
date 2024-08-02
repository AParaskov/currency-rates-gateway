package com.project.gateway.service.impl;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import com.project.gateway.model.dto.LatestCurrencyRateResponse;
import com.project.gateway.service.RatesService;
import com.project.gateway.service.db.HistoryDbService;
import com.project.gateway.service.db.RateDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RatesServiceImpl implements RatesService {
    @Value("${fixerio.url}")
    private String url;

    @Value("${fixerio.accessKey}")
    private String accessKey;

    private final RestTemplate restTemplate;
    private final HistoryDbService historyDbService;
    private final RateDbService rateDbService;

    @Override
    public LatestCurrencyRateResponse collectRates() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_key", accessKey);

        return restTemplate.getForObject(builder.toUriString(), LatestCurrencyRateResponse.class);

    }

    @Override
    public void persistRates() {
        LatestCurrencyRateResponse latestCurrencyRates = collectRates();

        Instant instant = Instant.ofEpochSecond(latestCurrencyRates.getTimestamp());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        History history = History.builder()
                .baseCurrency(latestCurrencyRates.getBase())
                .timestamp(localDateTime)
                .build();

        historyDbService.save(history);

        latestCurrencyRates.getRates().forEach((k, v) -> {
            Rate rate = Rate.builder()
                    .currency(k)
                    .rate(v)
                    .historyId(history)
                    .build();

            rateDbService.save(rate) ;
        });
    }
}
