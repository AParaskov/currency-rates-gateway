package com.project.gateway.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LatestCurrencyRateResponse {
    private boolean success;
    private Long timestamp;
    private String base;
    private Date date;
    private Map<String, BigDecimal> rates;
}
