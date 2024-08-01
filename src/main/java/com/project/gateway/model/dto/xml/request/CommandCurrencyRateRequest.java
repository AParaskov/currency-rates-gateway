package com.project.gateway.model.dto.xml.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@NoArgsConstructor
@Getter
@Setter
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
