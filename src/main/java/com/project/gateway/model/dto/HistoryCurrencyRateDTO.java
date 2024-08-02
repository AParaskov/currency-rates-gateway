package com.project.gateway.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryCurrencyRateDTO {
    private String timeOfUpdate;
    private List<CurrencyRateDTO> rates;
}
