package com.project.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import com.project.gateway.model.Statistics;
import com.project.gateway.model.dto.json.request.CurrentCurrencyRateRequest;
import com.project.gateway.model.dto.json.request.HistoryCurrencyRateRequest;
import com.project.gateway.model.dto.json.response.CurrentCurrencyRateResponse;
import com.project.gateway.model.dto.json.response.HistoryCurrencyRateResponse;
import com.project.gateway.model.dto.xml.request.CommandCurrencyRateRequest;
import com.project.gateway.model.dto.xml.response.CommandCurrencyRateResponse;
import com.project.gateway.producer.RabbitMqProducer;
import com.project.gateway.service.db.HistoryDbService;
import com.project.gateway.service.db.RateDbService;
import com.project.gateway.service.db.StatisticsDbService;
import com.project.gateway.service.db.impl.HistoryDbServiceImpl;
import com.project.gateway.service.db.impl.RateDbServiceImpl;
import com.project.gateway.service.impl.StatisticsServiceImpl;
import com.project.gateway.utils.TestUtils;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.project.gateway.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatisticsServiceImplTest {
    private StatisticsServiceImpl statisticsService;
    private StatisticsDbService statisticsDbService;
    private HistoryDbService historyDbService;
    private RateDbService rateDbService;
    private RabbitMqProducer producer;
    private ObjectMapper objectMapper;
    private DateTimeFormatter formatter;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        testUtils = new TestUtils();
        objectMapper = new ObjectMapper();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        producer = Mockito.mock(RabbitMqProducer.class);
        statisticsDbService = Mockito.mock(StatisticsDbService.class);
        historyDbService = Mockito.mock(HistoryDbServiceImpl.class);
        rateDbService = Mockito.mock(RateDbServiceImpl.class);
        statisticsService = new StatisticsServiceImpl(statisticsDbService, historyDbService, rateDbService, producer, objectMapper, formatter);
    }

    @Test
    public void collectStatisticsTest() throws JsonProcessingException {
        Statistics statistics = testUtils.buildStatistics();
        when(statisticsDbService.save(any())).thenReturn(statistics);

        statisticsService.collectStatistics(VALID_SERVICE_NAME, VALID_REQUEST_ID, VALID_TIMESTAMP, VALID_CLIENT);

        verify(statisticsDbService).save(any());
        verify(producer).sendMessage(any());
    }

    @Test
    public void getCurrentCurrencyRateTest() {
        CurrentCurrencyRateRequest request = testUtils.buildValidCurrentCurrencyRateRequest();
        CurrentCurrencyRateResponse response = testUtils.buildValidCurrentCurrencyRateResponse();
        History history = testUtils.buildHistory();
        Rate rate = testUtils.buildRate();

        when(historyDbService.findFirstByBaseCurrencyOrderByTimestampDesc(request.getCurrency())).thenReturn(history);
        when(rateDbService.findAllByHistoryId(history)).thenReturn(List.of(rate));

        CurrentCurrencyRateResponse actualResponse = statisticsService.getCurrentCurrencyRate(request);

        assertEquals(response.getRate().get(0).getRate(), actualResponse.getRate().get(0).getRate());
        assertEquals(response.getRate().get(0).getCurrency(), actualResponse.getRate().get(0).getCurrency());
        assertEquals(response.getRequestId(), actualResponse.getRequestId());
    }

    @Test
    public void getHistoryCurrencyRate() {
        HistoryCurrencyRateRequest request = testUtils.buildValidHistoryCurrencyRateRequest();
        HistoryCurrencyRateResponse response = testUtils.buildValidHistoryCurrencyRateResponse();
        History history = testUtils.buildHistory();
        Rate rate = testUtils.buildRate();

        when(historyDbService.findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime.parse("2024-07-31T01:00:00"), LocalDateTime.parse("2024-08-01T01:00:00")))
                .thenReturn(List.of(history));
        when(rateDbService.findAllByHistoryId(history)).thenReturn(List.of(rate));

        HistoryCurrencyRateResponse actualResponse = statisticsService.getHistoryCurrencyRate(request);

        assertEquals(response.getRateHistory().get(0).getRates().get(0).getRate(), actualResponse.getRateHistory().get(0).getRates().get(0).getRate());
        assertEquals(response.getRateHistory().get(0).getRates().get(0).getCurrency(), actualResponse.getRateHistory().get(0).getRates().get(0).getCurrency());
        assertEquals(response.getRequestId(), actualResponse.getRequestId());
        assertEquals(response.getPeriod(), actualResponse.getPeriod());
    }

    @Test
    public void getCurrencyRateByCommandCurrentTest() throws JAXBException {
        CommandCurrencyRateRequest currentRequest = testUtils.buildValidCommandCurrentCurrencyRateRequest(testUtils.buildValidCurrentRateDTO());
        CommandCurrencyRateResponse currentResponse = testUtils.buildValidCommandCurrentCurrencyRateResponse();
        History history = testUtils.buildHistory();
        Rate rate = testUtils.buildRate();

        when(historyDbService.findFirstByBaseCurrencyOrderByTimestampDesc(currentRequest.getCurrent().getCurrency())).thenReturn(history);
        when(rateDbService.findAllByHistoryId(history)).thenReturn(List.of(rate));

        CommandCurrencyRateResponse actualResponse = statisticsService.getCurrencyRateByCommand("LATEST", currentRequest);

        assertNull(actualResponse.getHistory());
        assertEquals(currentResponse.getBaseCurrency(), actualResponse.getBaseCurrency());
        assertEquals(currentResponse.getRequestId(), actualResponse.getRequestId());
        assertEquals(currentResponse.getLatest().get(0).getRate(), actualResponse.getLatest().get(0).getRate());
    }

    @Test
    public void getCurrencyRateByCommandHistoryTest() throws JAXBException {
        CommandCurrencyRateRequest historyRequest = testUtils.buildValidCommandHistoryCurrencyRateRequest(testUtils.buildValidHistoryRateDTO());
        CommandCurrencyRateResponse historyResponse = testUtils.buildValidCommandHistoryCurrencyRateResponse();
        History history = testUtils.buildHistory();
        Rate rate = testUtils.buildRate();

        when(historyDbService.findAllByTimestampBetweenOrderByTimestampDesc(any(), any()))
                .thenReturn(List.of(history));
        when(rateDbService.findAllByHistoryId(history)).thenReturn(List.of(rate));

        CommandCurrencyRateResponse actualResponse = statisticsService.getCurrencyRateByCommand("HISTORY", historyRequest);

        assertNull(actualResponse.getLatest());
        assertEquals(historyResponse.getBaseCurrency(), actualResponse.getBaseCurrency());
        assertEquals(historyResponse.getPeriod(), actualResponse.getPeriod());
        assertEquals(historyResponse.getRequestId(), actualResponse.getRequestId());
        assertEquals(historyResponse.getHistory().get(0).getRates().get(0).getRate(), actualResponse.getHistory().get(0).getRates().get(0).getRate());
    }

    @Test
    public void isStatisticsPresentTest() {
        statisticsService.isStatisticsPresent(VALID_REQUEST_ID);

        verify(statisticsDbService).findByRequestId(any());
    }
}
