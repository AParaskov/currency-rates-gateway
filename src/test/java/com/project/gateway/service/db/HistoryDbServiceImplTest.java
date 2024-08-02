package com.project.gateway.service.db;

import com.project.gateway.model.History;
import com.project.gateway.repository.HistoryRepository;
import com.project.gateway.service.db.impl.HistoryDbServiceImpl;
import com.project.gateway.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HistoryDbServiceImplTest {
    private HistoryDbServiceImpl historyDbServiceImpl;
    private HistoryRepository historyRepository;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        testUtils = new TestUtils();
        historyRepository = Mockito.mock(HistoryRepository.class);
        historyDbServiceImpl = new HistoryDbServiceImpl(historyRepository);
    }

    @Test
    public void saveTest() {
        Long historyId = 1L;
        String baseCurrency = "EUR";
        History history = testUtils.buildHistory();
        when(historyRepository.save(history))
                .thenReturn(history);

        History savedHistory = historyDbServiceImpl.save(history);

        assertEquals(historyId, savedHistory.getId());
        assertEquals(baseCurrency, savedHistory.getBaseCurrency());
    }

    @Test
    public void findFirstByBaseCurrencyOrderByTimestampDescTest() {
        Long historyId = 1L;
        String baseCurrency = "EUR";
        History history = testUtils.buildHistory();
        when(historyRepository.findFirstByBaseCurrencyOrderByTimestampDesc(history.getBaseCurrency()))
                .thenReturn(history);

        History foundHistory = historyDbServiceImpl.findFirstByBaseCurrencyOrderByTimestampDesc(baseCurrency);

        assertEquals(foundHistory.getBaseCurrency(), baseCurrency);
        assertEquals(foundHistory.getId(), historyId);
    }

    @Test
    public void findAllByTimestampBetweenOrderByTimestampDescTest() {
        Long historyId = 1L;
        String baseCurrency = "EUR";
        History history = testUtils.buildHistory();
        when(historyRepository.findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime.parse("2024-07-31T01:00:00"), LocalDateTime.parse("2024-08-01T01:00:00")))
                .thenReturn(List.of(history));

        List<History> foundHistory = historyDbServiceImpl.findAllByTimestampBetweenOrderByTimestampDesc(LocalDateTime.parse("2024-07-31T01:00:00"), LocalDateTime.parse("2024-08-01T01:00:00"));

        assertEquals(foundHistory.get(0).getBaseCurrency(), baseCurrency);
        assertEquals(foundHistory.get(0).getId(), historyId);
    }
}
