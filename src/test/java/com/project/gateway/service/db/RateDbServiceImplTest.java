package com.project.gateway.service.db;

import com.project.gateway.model.History;
import com.project.gateway.model.Rate;
import com.project.gateway.repository.RateRepository;
import com.project.gateway.service.db.impl.RateDbServiceImpl;
import com.project.gateway.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RateDbServiceImplTest {
    private RateDbServiceImpl rateDbServiceImpl;
    private RateRepository rateRepository;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        testUtils = new TestUtils();
        rateRepository = Mockito.mock(RateRepository.class);
        rateDbServiceImpl = new RateDbServiceImpl(rateRepository);
    }

    @Test
    public void saveTest() {
        Long rateId = 1L;
        String currency = "EUR";
        Rate rate = testUtils.buildRate();
        when(rateRepository.save(rate))
                .thenReturn(rate);

        Rate savedRate = rateDbServiceImpl.save(rate);

        assertEquals(rateId, savedRate.getId());
        assertEquals(currency, savedRate.getCurrency());
    }

    @Test
    public void findAllByHistoryIdTest() {
        Long rateId = 1L;
        String currency = "EUR";
        Rate rate = testUtils.buildRate();
        History history = testUtils.buildHistory();
        when(rateRepository.findAllByHistoryId(history))
                .thenReturn(List.of(rate));

        List<Rate> foundRates = rateDbServiceImpl.findAllByHistoryId(history);

        assertEquals(1, foundRates.size());
        assertEquals(rateId, foundRates.get(0).getId());
        assertEquals(currency, foundRates.get(0).getCurrency());
    }
}
