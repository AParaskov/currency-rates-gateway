package com.project.gateway.controller;

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

import static com.project.gateway.utils.TestUtils.XML_COMMAND_CURRENCY_RATES_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = XmlCurrencyRateController.class)
public class XmlCurrencyRateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticsService statisticsService;
    @MockBean
    private DateTimeFormatter dateTimeFormatter;
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() throws Exception {
        testUtils = new TestUtils();
    }

    @Test
    public void commandCurrentCurrencyRateShouldReturn200okWithValidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(XML_COMMAND_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(testUtils.marshalRequestToXmlString(testUtils.buildValidCommandCurrentCurrencyRateRequest(testUtils.buildValidCurrentRateDTO()))))
                .andExpect(status().isOk());
    }

    @Test
    public void commandCurrentCurrencyRateShouldReturn400badRequestWithInvalidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(XML_COMMAND_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(testUtils.marshalRequestToXmlString(testUtils.buildValidCommandCurrentCurrencyRateRequest(testUtils.buildInvalidCurrentRateDTO()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void commandHistoryCurrencyRateShouldReturn200okRequestWithValidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(XML_COMMAND_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(testUtils.marshalRequestToXmlString(testUtils.buildValidCommandHistoryCurrencyRateRequest(testUtils.buildValidHistoryRateDTO()))))
                .andExpect(status().isOk());
    }

    @Test
    public void commandHistoryCurrencyRateShouldReturn400badRequestWithInvalidRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(XML_COMMAND_CURRENCY_RATES_URL)
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(testUtils.marshalRequestToXmlString(testUtils.buildValidCommandHistoryCurrencyRateRequest(testUtils.buildInvalidHistoryRateDTO()))))
                .andExpect(status().isBadRequest());
    }
}
