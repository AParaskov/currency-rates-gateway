package com.project.gateway.exceptions;

public class RequestDuplicateException extends RuntimeException {

    public RequestDuplicateException(String message) {
        super(message);
    }
}
