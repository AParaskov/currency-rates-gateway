package com.project.gateway.model.dto.xml.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommandCurrencyRateRequest {
    @XmlAttribute(name = "id")
    @NotBlank(message = REQUEST_FIELD_VALIDATION_MESSAGE)
    @Size(min = 3, max = 100, message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String requestId;

    @XmlElement(name = "get")
    private @Valid CurrentRateDTO current;

    @XmlElement
    private @Valid HistoryRateDTO history;
}
