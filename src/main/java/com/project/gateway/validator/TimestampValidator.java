package com.project.gateway.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimestampValidator implements ConstraintValidator<ValidateTimestamp, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        return String.valueOf(value).length() >= 13;
    }
}
