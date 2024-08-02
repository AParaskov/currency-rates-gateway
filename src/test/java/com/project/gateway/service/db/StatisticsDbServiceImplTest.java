package com.project.gateway.service.db;

import com.project.gateway.model.Statistics;
import com.project.gateway.repository.StatisticsRepository;
import com.project.gateway.service.db.impl.StatisticsDbServiceImpl;
import com.project.gateway.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticsDbServiceImplTest {
    private StatisticsDbServiceImpl statisticsDbServiceImpl;
    private StatisticsRepository statisticsRepository;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        testUtils = new TestUtils();
        statisticsRepository = Mockito.mock(StatisticsRepository.class);
        statisticsDbServiceImpl = new StatisticsDbServiceImpl(statisticsRepository);
    }

    @Test
    public void saveTest() {
        Long statisticsId = 1L;
        String requestId = "requestId";
        Statistics statistics = testUtils.buildStatistics();
        when(statisticsRepository.saveAndFlush(statistics))
                .thenReturn(statistics);

        Statistics savedStatistics = statisticsDbServiceImpl.save(statistics);

        assertEquals(statisticsId, savedStatistics.getId());
        assertEquals(requestId, savedStatistics.getRequestId());
    }

    @Test
    public void findFirstByBaseCurrencyOrderByTimestampDescTest() {
        Long statisticsId = 1L;
        String requestId = "requestId";
        Statistics statistics = testUtils.buildStatistics();
        when(statisticsRepository.findByRequestId(requestId))
                .thenReturn(Optional.of(statistics));

        Optional<Statistics> foundStatistics = statisticsDbServiceImpl.findByRequestId(requestId);

        assertEquals(statisticsId, foundStatistics.get().getId());
        assertEquals(requestId, foundStatistics.get().getRequestId());
    }
}
