package com.project.gateway.model.dto.json.response;

import com.project.gateway.model.dto.HistoryCurrencyRateDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HistoryCurrencyRateResponse {
    private String requestId;
    private String baseCurrency;
    private Long period;
    private List<HistoryCurrencyRateDTO> rateHistory;
}
