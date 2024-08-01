package com.project.gateway.model.dto;

import lombok.*;

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
