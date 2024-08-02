package com.project.gateway.model.dto.xml.response;

import com.project.gateway.model.dto.CurrencyRateDTO;
import com.project.gateway.model.dto.HistoryCurrencyRateDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandCurrencyRateResponse {
    @XmlAttribute
    private String requestId;
    @XmlElement
    private String baseCurrency;
    @XmlElement
    private Long period;
    @XmlElement
    private List<CurrencyRateDTO> latest;
    @XmlElement
    private List<HistoryCurrencyRateDTO> history;
}
