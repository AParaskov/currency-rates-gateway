package com.project.gateway.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CurrencyRateDTO {
    private String currency;
    private BigDecimal rate;
}
