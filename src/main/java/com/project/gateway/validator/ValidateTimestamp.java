package com.project.gateway.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.REQUEST_FIELD_VALIDATION_MESSAGE;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimestampValidator.class)
@Documented
public @interface ValidateTimestamp {

    String message() default REQUEST_FIELD_VALIDATION_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
