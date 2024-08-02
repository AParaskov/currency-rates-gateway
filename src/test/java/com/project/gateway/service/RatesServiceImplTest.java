package com.project.gateway.service;

import com.project.gateway.model.dto.LatestCurrencyRateResponse;
import com.project.gateway.service.db.HistoryDbService;
import com.project.gateway.service.db.RateDbService;
import com.project.gateway.service.db.impl.HistoryDbServiceImpl;
import com.project.gateway.service.db.impl.RateDbServiceImpl;
import com.project.gateway.service.impl.RatesServiceImpl;
import com.project.gateway.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatesServiceImplTest {
    private RatesServiceImpl ratesService;
    private RestTemplate restTemplate;
    private HistoryDbService historyDbService;
    private RateDbService rateDbService;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        testUtils = new TestUtils();
        restTemplate = Mockito.mock(RestTemplate.class);
        historyDbService = Mockito.mock(HistoryDbServiceImpl.class);
        rateDbService = Mockito.mock(RateDbServiceImpl.class);
        ratesService = new RatesServiceImpl(restTemplate, historyDbService, rateDbService);
        ReflectionTestUtils.setField(ratesService, "url", "http://data.fixer.io/api/latest");
        ReflectionTestUtils.setField(ratesService, "accessKey", "accessKey");
    }

    @Test
    public void collectRatesTest() {
        LatestCurrencyRateResponse response = testUtils.buildLatestCurrencyRateResponse();

        when(restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=accessKey", LatestCurrencyRateResponse.class)).thenReturn(response);

        LatestCurrencyRateResponse actualResponse = ratesService.collectRates();

        assertEquals(actualResponse.getBase(), response.getBase());
        assertEquals(actualResponse.getTimestamp(), response.getTimestamp());
    }

    @Test
    public void persistRatesTest() {
        LatestCurrencyRateResponse response = testUtils.buildLatestCurrencyRateResponse();

        when(ratesService.collectRates()).thenReturn(response);

        ratesService.persistRates();

        verify(rateDbService).save(any());
        verify(historyDbService).save(any());
    }
}
