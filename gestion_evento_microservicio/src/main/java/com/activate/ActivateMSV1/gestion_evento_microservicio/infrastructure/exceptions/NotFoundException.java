package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
