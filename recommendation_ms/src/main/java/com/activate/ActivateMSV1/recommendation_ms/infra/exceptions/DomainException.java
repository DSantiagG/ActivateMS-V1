package com.activate.ActivateMSV1.recommendation_ms.infra.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
