package com.project.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.gateway.exceptions.RequestDuplicateException;
import com.project.gateway.model.dto.xml.request.CommandCurrencyRateRequest;
import com.project.gateway.model.dto.xml.response.CommandCurrencyRateResponse;
import com.project.gateway.service.StatisticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.DUPLICATE_REQUEST_ID_ERROR_MESSAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/xml_api")
public class XmlCurrencyRateController {
    private static final String SERVICE_NAME = "EXT_SERVICE_1";
    private final StatisticsService statisticsService;

    @PostMapping(value = "/command",consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<CommandCurrencyRateResponse> currentCurrencyRate(@Valid @RequestBody CommandCurrencyRateRequest request) throws JsonProcessingException {
        if (statisticsService.isStatisticsPresent(request.getRequestId())) {
            throw new RequestDuplicateException(DUPLICATE_REQUEST_ID_ERROR_MESSAGE);
        }

        if (request.getCurrent() == null && request.getHistory() != null) {
            statisticsService.collectStatistics(SERVICE_NAME, request.getRequestId(), LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli(), request.getHistory().getClientId());
            return ResponseEntity.ok(statisticsService.getCurrencyRateByCommand("HISTORY", request));
        }
        statisticsService.collectStatistics(SERVICE_NAME, request.getRequestId(), LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli(), request.getCurrent().getClientId());
        return ResponseEntity.ok(statisticsService.getCurrencyRateByCommand("LATEST", request));
    }
}
