package com.project.gateway.model.dto.xml.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrentRateDTO {
    @XmlAttribute(name = "consumer")
    @NotBlank(message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String clientId;
    @XmlElement
    @Pattern(regexp = "^[A-Z]{3}$", message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String currency;
}
