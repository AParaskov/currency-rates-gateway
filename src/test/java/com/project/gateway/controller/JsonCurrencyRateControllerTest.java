package com.project.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gateway.service.StatisticsService;
import com.project.gateway.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeFormatter;

import static com.project.gateway.utils.TestUtils.JSON_CURRENT_CURRENCY_RATES_URL;
import static com.project.gateway.utils.TestUtils.JSON_HISTORY_CURRENCY_RATES_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JsonCurrencyRateController.class)
public class JsonCurrencyRateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticsService statisticsService;
    @MockBean
    private DateTimeFormatter dateTimeFormatter;
    private ObjectMapper objectMapper;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() throws Exception {
        testUtils = new TestUtils();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void currentCurrencyRateShouldReturn200okWithValidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(JSON_CURRENT_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testUtils.buildValidCurrentCurrencyRateRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void currentCurrencyRateShouldReturn400badRequestWithInvalidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(JSON_CURRENT_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testUtils.buildInvalidCurrentCurrencyRateRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void historyCurrencyRateShouldReturn200okRequestWithValidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(JSON_HISTORY_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testUtils.buildValidHistoryCurrencyRateRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void historyCurrencyRateShouldReturn400badRequestWithInvalidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(JSON_HISTORY_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testUtils.buildInvalidHistoryCurrencyRateRequest())))
                .andExpect(status().isBadRequest());
    }
}
