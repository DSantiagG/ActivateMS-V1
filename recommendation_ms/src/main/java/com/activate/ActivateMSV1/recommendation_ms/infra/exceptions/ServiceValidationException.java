package com.activate.ActivateMSV1.recommendation_ms.infra.exceptions;

public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException(String message) {
        super(message);
    }
}
