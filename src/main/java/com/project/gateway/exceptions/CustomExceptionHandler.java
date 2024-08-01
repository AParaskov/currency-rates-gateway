package com.project.gateway.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.project.gateway.exceptions.ErrorCodesAndMessages.*;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final DateTimeFormatter formatter;

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleJsonProcessingExceptions(JsonProcessingException ex, WebRequest request) {
        String errorMessageDescription = checkIfErrorMessageDescriptionIsValid(ex.getLocalizedMessage());

        CustomError errorMessage = CustomError.builder()
                .timestamp(LocalDateTime.now(ZoneOffset.UTC).format(formatter))
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(SYSTEM_ERROR_MESSAGE)
                .description(errorMessageDescription)
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerExceptions(NullPointerException ex, WebRequest request) {
        String errorMessageDescription = checkIfErrorMessageDescriptionIsValid(ex.getMessage());

        CustomError errorMessage = CustomError.builder()
                .timestamp(LocalDateTime.now(ZoneOffset.UTC).format(formatter))
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(SYSTEM_ERROR_MESSAGE)
                .description(errorMessageDescription)
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequestDuplicateException.class)
    public ResponseEntity<Object> handleDuplicateException(RequestDuplicateException ex, WebRequest request) {
        String errorMessageDescription = checkIfErrorMessageDescriptionIsValid(ex.getMessage());

        CustomError errorMessage = CustomError.builder()
                .timestamp(LocalDateTime.now(ZoneOffset.UTC).format(formatter))
                .errorCode(BAD_REQUEST_ERROR)
                .errorMessage(VALIDATION_ERROR_MESSAGE)
                .description(errorMessageDescription)
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessageDescription = checkIfErrorMessageDescriptionIsValid(ex.getBindingResult().getFieldError().getDefaultMessage());

        CustomError errorMessage = CustomError.builder()
                .timestamp(LocalDateTime.now(ZoneOffset.UTC).format(formatter))
                .errorCode(BAD_REQUEST_ERROR)
                .errorMessage(VALIDATION_ERROR_MESSAGE)
                .description(errorMessageDescription)
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private String checkIfErrorMessageDescriptionIsValid(String errorMessageDescription) {
        return errorMessageDescription == null ? "" : errorMessageDescription;
    }
}
