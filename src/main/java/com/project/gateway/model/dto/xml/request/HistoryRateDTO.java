package com.project.gateway.model.dto.xml.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryRateDTO {
    @XmlAttribute(name = "consumer")
    @NotBlank(message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String clientId;

    @XmlAttribute()
    @Pattern(regexp = "^[A-Z]{3}$", message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String currency;

    @XmlAttribute
    @Min(value = 1, message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private Long period;
}
