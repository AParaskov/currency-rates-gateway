package com.project.gateway.model.dto.json.response;

import com.project.gateway.model.dto.CurrencyRateDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CurrentCurrencyRateResponse {
    private String requestId;
    private String baseCurrency;
    private String lastUpdated;
    private List<CurrencyRateDTO> rate;
}
