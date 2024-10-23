package com.activate.ActivateMSV1.notification_ms.infra.exceptions;

public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException(String message) {
        super(message);
    }
}
