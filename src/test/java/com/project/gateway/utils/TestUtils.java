package com.project.gateway.utils;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import com.project.gateway.model.Statistics;
import com.project.gateway.model.dto.CurrencyRateDTO;
import com.project.gateway.model.dto.HistoryCurrencyRateDTO;
import com.project.gateway.model.dto.LatestCurrencyRateResponse;
import com.project.gateway.model.dto.json.request.CurrentCurrencyRateRequest;
import com.project.gateway.model.dto.json.request.HistoryCurrencyRateRequest;
import com.project.gateway.model.dto.json.response.CurrentCurrencyRateResponse;
import com.project.gateway.model.dto.json.response.HistoryCurrencyRateResponse;
import com.project.gateway.model.dto.xml.request.CommandCurrencyRateRequest;
import com.project.gateway.model.dto.xml.request.CurrentRateDTO;
import com.project.gateway.model.dto.xml.request.HistoryRateDTO;
import com.project.gateway.model.dto.xml.response.CommandCurrencyRateResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestUtils {
    public static final String JSON_CURRENT_CURRENCY_RATES_URL = "/json_api/current";
    public static final String JSON_HISTORY_CURRENCY_RATES_URL = "/json_api/history";
    public static final String XML_COMMAND_CURRENCY_RATES_URL = "/xml_api/command";
    public static final String VALID_BASE_CURRENCY = "EUR";
    public static final String VALID_REQUEST_ID = "requestId";
    public static final Long VALID_TIMESTAMP = 1722474000000L;
    public static final Long VALID_PERIOD = 24L;
    public static final String VALID_CLIENT = "client";
    public static final String VALID_SERVICE_NAME = "serviceName";
    public static final String VALID_DATE_FORMAT = "2024-08-01T00:00:00";
    public static final BigDecimal VALID_RATE = BigDecimal.valueOf(0.2556450000);
    public static final String INVALID_BASE_CURRENCY = "euro";

    public CurrentCurrencyRateRequest buildValidCurrentCurrencyRateRequest() {
        return CurrentCurrencyRateRequest.builder()
                .currency(VALID_BASE_CURRENCY)
                .requestId(VALID_REQUEST_ID)
                .timestamp(VALID_TIMESTAMP)
                .client(VALID_CLIENT)
                .build();
    }

    public CurrentCurrencyRateRequest buildInvalidCurrentCurrencyRateRequest() {
        return CurrentCurrencyRateRequest.builder()
                .currency(INVALID_BASE_CURRENCY)
                .requestId(VALID_REQUEST_ID)
                .timestamp(VALID_TIMESTAMP)
                .client(VALID_CLIENT)
                .build();
    }

    public CurrentCurrencyRateResponse buildValidCurrentCurrencyRateResponse() {
        return CurrentCurrencyRateResponse.builder()
                .baseCurrency(VALID_BASE_CURRENCY)
                .requestId(VALID_REQUEST_ID)
                .lastUpdated(VALID_DATE_FORMAT)
                .rate(List.of(buildValidCurrencyRateDTO()))
                .build();
    }

    public HistoryCurrencyRateResponse buildValidHistoryCurrencyRateResponse() {
        return HistoryCurrencyRateResponse.builder()
                .requestId(VALID_REQUEST_ID)
                .baseCurrency(VALID_BASE_CURRENCY)
                .period(VALID_PERIOD)
                .rateHistory(List.of(buildValidHistoryCurrencyRateDTO()))
                .build();
    }

    public HistoryCurrencyRateRequest buildValidHistoryCurrencyRateRequest() {
        return HistoryCurrencyRateRequest.builder()
                .currency(VALID_BASE_CURRENCY)
                .requestId(VALID_REQUEST_ID)
                .timestamp(VALID_TIMESTAMP)
                .client(VALID_CLIENT)
                .period(VALID_PERIOD)
                .build();
    }

    public HistoryCurrencyRateRequest buildInvalidHistoryCurrencyRateRequest() {
        return HistoryCurrencyRateRequest.builder()
                .currency(INVALID_BASE_CURRENCY)
                .requestId(VALID_REQUEST_ID)
                .timestamp(VALID_TIMESTAMP)
                .client(VALID_CLIENT)
                .period(VALID_PERIOD)
                .build();
    }

    public CommandCurrencyRateRequest buildValidCommandHistoryCurrencyRateRequest(HistoryRateDTO historyRateDTO) throws JAXBException {
        return CommandCurrencyRateRequest.builder()
                .requestId(VALID_REQUEST_ID)
                .history(historyRateDTO)
                .build();
    }

    public CommandCurrencyRateRequest buildValidCommandCurrentCurrencyRateRequest(CurrentRateDTO currentRateDTO) throws JAXBException {
        return CommandCurrencyRateRequest.builder()
                .current(currentRateDTO)
                .requestId(VALID_REQUEST_ID)
                .build();
    }

    public CommandCurrencyRateResponse buildValidCommandCurrentCurrencyRateResponse() throws JAXBException {
        return CommandCurrencyRateResponse.builder()
                .requestId(VALID_REQUEST_ID)
                .baseCurrency(VALID_BASE_CURRENCY)
                .period(VALID_PERIOD)
                .latest(List.of(buildValidCurrencyRateDTO()))
                .build();
    }

    public CommandCurrencyRateResponse buildValidCommandHistoryCurrencyRateResponse() throws JAXBException {
        return CommandCurrencyRateResponse.builder()
                .requestId(VALID_REQUEST_ID)
                .baseCurrency(VALID_BASE_CURRENCY)
                .period(VALID_PERIOD)
                .history(List.of(buildValidHistoryCurrencyRateDTO()))
                .build();
    }

    public LatestCurrencyRateResponse buildLatestCurrencyRateResponse() {
        return LatestCurrencyRateResponse.builder()
                .success(true)
                .base(VALID_BASE_CURRENCY)
                .timestamp(VALID_TIMESTAMP)
                .date(Date.from(LocalDateTime.parse(VALID_DATE_FORMAT).toInstant(ZoneOffset.UTC)))
                .rates(Map.of("BGN", VALID_RATE))
                .build();
    }
    public History buildHistory() {
        return History.builder()
                .id(1L)
                .baseCurrency(VALID_BASE_CURRENCY)
                .timestamp(LocalDateTime.parse(VALID_DATE_FORMAT))
                .rates(List.of(new Rate()))
                .build();
    }

    public Rate buildRate() {
        return Rate.builder()
                .historyId(new History())
                .rate(VALID_RATE)
                .currency(VALID_BASE_CURRENCY)
                .id(1L)
                .build();
    }

    public Statistics buildStatistics() {
        return Statistics.builder()
                .id(1L)
                .requestId(VALID_REQUEST_ID)
                .serviceName(VALID_SERVICE_NAME)
                .time(LocalDateTime.parse(VALID_DATE_FORMAT))
                .clientId(VALID_CLIENT)
                .build();
    }

    public CurrentRateDTO buildValidCurrentRateDTO() {
        return CurrentRateDTO.builder()
                .clientId(VALID_CLIENT)
                .currency(VALID_BASE_CURRENCY)
                .build();
    }

    public HistoryRateDTO buildValidHistoryRateDTO() {
        return HistoryRateDTO.builder()
                .clientId(VALID_CLIENT)
                .currency(VALID_BASE_CURRENCY)
                .period(VALID_PERIOD)
                .build();
    }

    public CurrentRateDTO buildInvalidCurrentRateDTO() {
        return CurrentRateDTO.builder()
                .clientId(VALID_CLIENT)
                .currency(INVALID_BASE_CURRENCY)
                .build();
    }

    public HistoryRateDTO buildInvalidHistoryRateDTO() {
        return HistoryRateDTO.builder()
                .clientId(VALID_CLIENT)
                .currency(INVALID_BASE_CURRENCY)
                .period(VALID_PERIOD)
                .build();
    }

    public CurrencyRateDTO buildValidCurrencyRateDTO() {
        return CurrencyRateDTO.builder()
                .currency(VALID_BASE_CURRENCY)
                .rate(VALID_RATE)
                .build();
    }

    public HistoryCurrencyRateDTO buildValidHistoryCurrencyRateDTO() {
        return HistoryCurrencyRateDTO.builder()
                .timeOfUpdate(VALID_DATE_FORMAT)
                .rates(List.of(buildValidCurrencyRateDTO()))
                .build();
    }

    public String marshalRequestToXmlString(CommandCurrencyRateRequest commandCurrencyRateRequest) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CommandCurrencyRateRequest.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(commandCurrencyRateRequest, sw);
        return sw.toString();
    }

}
