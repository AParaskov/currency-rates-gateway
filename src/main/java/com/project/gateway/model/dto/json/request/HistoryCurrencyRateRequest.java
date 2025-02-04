package com.project.gateway.model.dto.json.request;

import com.project.gateway.validator.ValidateTimestamp;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryCurrencyRateRequest {
    @NotBlank(message = REQUEST_FIELD_VALIDATION_MESSAGE)
    @Size(min = 3, max = 100, message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String requestId;

    @ValidateTimestamp
    private Long timestamp;

    @NotBlank(message = REQUEST_FIELD_VALIDATION_MESSAGE)
    @Size(min = 3, max = 30, message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String client;

    @Pattern(regexp = "^[A-Z]{3}$", message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private String currency;

    @Min(value = 1, message = REQUEST_FIELD_VALIDATION_MESSAGE)
    private Long period;
}
