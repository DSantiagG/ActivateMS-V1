package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
