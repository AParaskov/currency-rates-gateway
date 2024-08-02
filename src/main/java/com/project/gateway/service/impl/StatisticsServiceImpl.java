package com.project.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gateway.model.History;
import com.project.gateway.model.Statistics;
import com.project.gateway.model.dto.StatisticsMessageDTO;
import com.project.gateway.model.dto.json.request.CurrentCurrencyRateRequest;
import com.project.gateway.model.dto.json.request.HistoryCurrencyRateRequest;
import com.project.gateway.model.dto.CurrencyRateDTO;
import com.project.gateway.model.dto.json.response.CurrentCurrencyRateResponse;
import com.project.gateway.model.dto.HistoryCurrencyRateDTO;
import com.project.gateway.model.dto.json.response.HistoryCurrencyRateResponse;
import com.project.gateway.model.dto.xml.request.CommandCurrencyRateRequest;
import com.project.gateway.model.dto.xml.response.CommandCurrencyRateResponse;
import com.project.gateway.producer.RabbitMqProducer;
import com.project.gateway.service.StatisticsService;
import com.project.gateway.service.db.HistoryDbService;
import com.project.gateway.service.db.RateDbService;
import com.project.gateway.service.db.StatisticsDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsDbService statisticsDbService;
    private final HistoryDbService historyDbService;
    private final RateDbService rateDbService;
    private final RabbitMqProducer producer;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter formatter;

    @Override
    public void collectStatistics(String serviceName, String requestId, Long time, String clientId) throws JsonProcessingException {
        Instant instant = Instant.ofEpochMilli(time).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime requestDateTime =
                LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        Statistics statistics = statisticsDbService.save(Statistics.builder()
                .serviceName(serviceName)
                .requestId(requestId)
                .time(requestDateTime)
                .clientId(clientId)
                .build());

        StatisticsMessageDTO statisticsMessage = StatisticsMessageDTO.builder()
                .serviceName(statistics.getServiceName())
                .clientId(statistics.getClientId())
                .time(statistics.getTime().format(formatter))
                .requestId(statistics.getRequestId())
                .build();

        producer.sendMessage(objectMapper.writeValueAsString(statisticsMessage));
    }

    @Override
    public CurrentCurrencyRateResponse getCurrentCurrencyRate(CurrentCurrencyRateRequest request) {
        History latestCurrencyRatesHistory = getLatestCurrencyRatesHistory(request.getCurrency());
        return CurrentCurrencyRateResponse.builder()
                .requestId(request.getRequestId())
                .baseCurrency(request.getCurrency())
                .lastUpdated(latestCurrencyRatesHistory.getTimestamp().format(formatter))
                .rate(getLatestRates(latestCurrencyRatesHistory))
                .build();
    }

    @Override
    public HistoryCurrencyRateResponse getHistoryCurrencyRate(HistoryCurrencyRateRequest request) {
        return HistoryCurrencyRateResponse.builder()
                .requestId(request.getRequestId())
                .baseCurrency(request.getCurrency())
                .period(request.getPeriod())
                .rateHistory(getCurrencyRatesHistory(getTimePeriodCurrencyRatesHistory(request.getTimestamp(), request.getPeriod())))
                .build();
    }

    @Override
    public CommandCurrencyRateResponse getCurrencyRateByCommand(String command, CommandCurrencyRateRequest request) {
        if (command.equals("HISTORY")) {
            Long requestTimestamp = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();

            List<History> timePeriodCurrencyRatesHistory = getTimePeriodCurrencyRatesHistory(requestTimestamp, request.getHistory().getPeriod());

            return CommandCurrencyRateResponse.builder()
                    .requestId(request.getRequestId())
                    .baseCurrency(request.getHistory().getCurrency())
                    .period(request.getHistory().getPeriod())
                    .history(getCurrencyRatesHistory(timePeriodCurrencyRatesHistory))
                    .build();
        }

        History latestCurrencyRatesHistory = getLatestCurrencyRatesHistory(request.getCurrent().getCurrency());

        return CommandCurrencyRateResponse.builder()
                .requestId(request.getRequestId())
                .baseCurrency(request.getCurrent().getCurrency())
                .latest(getLatestRates(latestCurrencyRatesHistory))
                .build();
    }

    @Override
    public boolean isStatisticsPresent(String requestId) {
        return statisticsDbService.findByRequestId(requestId).isPresent();
    }

    private List<CurrencyRateDTO> findRatesByHistoryId(History history) {
        return rateDbService.findAllByHistoryId(history)
                .stream().map(rate -> CurrencyRateDTO.builder()
                        .currency(rate.getCurrency())
                        .rate(rate.getRate())
                        .build()).toList();
    }

    private History getLatestCurrencyRatesHistory(String baseCurrency) {
        return historyDbService.findFirstByBaseCurrencyOrderByTimestampDesc(baseCurrency);
    }

    private List<CurrencyRateDTO> getLatestRates(History latestCurrencyRatesHistory) {
        return findRatesByHistoryId(latestCurrencyRatesHistory);
    }

    private List<History> getTimePeriodCurrencyRatesHistory(Long timestamp, Long period) {
        Instant instant = Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime to = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        LocalDateTime from = to.minusHours(period);
        return historyDbService.findAllByTimestampBetweenOrderByTimestampDesc(from, to);
    }

    private List<HistoryCurrencyRateDTO> getCurrencyRatesHistory(List<History> timePeriodHistory) {
        return timePeriodHistory.stream().map(history -> HistoryCurrencyRateDTO.builder()
                        .timeOfUpdate(history.getTimestamp().format(formatter))
                        .rates(findRatesByHistoryId(history))
                        .build()).toList();
    }
}
