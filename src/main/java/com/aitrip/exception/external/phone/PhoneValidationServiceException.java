package com.aitrip.exception.external.phone;

public class PhoneValidationServiceException extends RuntimeException {
    public PhoneValidationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}