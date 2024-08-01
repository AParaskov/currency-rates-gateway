package com.project.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.gateway.exceptions.RequestDuplicateException;
import com.project.gateway.model.dto.json.request.CurrentCurrencyRateRequest;
import com.project.gateway.model.dto.json.request.HistoryCurrencyRateRequest;
import com.project.gateway.model.dto.json.response.CurrentCurrencyRateResponse;
import com.project.gateway.model.dto.json.response.HistoryCurrencyRateResponse;
import com.project.gateway.service.StatisticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.DUPLICATE_REQUEST_ID_ERROR_MESSAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/json_api")
public class JsonCurrencyRateController {
    private static final String SERVICE_NAME = "EXT_SERVICE_2";
    private final StatisticsService statisticsService;

    @PostMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrentCurrencyRateResponse> currentCurrencyRate(@Valid @RequestBody CurrentCurrencyRateRequest request) throws JsonProcessingException {
        if (statisticsService.isStatisticsPresent(request.getRequestId())) {
            throw new RequestDuplicateException(DUPLICATE_REQUEST_ID_ERROR_MESSAGE);
        }

        statisticsService.collectStatistics(SERVICE_NAME, request.getRequestId(), request.getTimestamp(), request.getClient());

        return ResponseEntity.ok(statisticsService.getCurrentCurrencyRate(request));
    }

    @PostMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryCurrencyRateResponse> currentCurrencyRate(@Valid @RequestBody HistoryCurrencyRateRequest request) throws JsonProcessingException {
        if (statisticsService.isStatisticsPresent(request.getRequestId())) {
            throw new RequestDuplicateException(DUPLICATE_REQUEST_ID_ERROR_MESSAGE);
        }

        statisticsService.collectStatistics(SERVICE_NAME, request.getRequestId(), request.getTimestamp(), request.getClient());

        return ResponseEntity.ok(statisticsService.getHistoryCurrencyRate(request));
    }
}
