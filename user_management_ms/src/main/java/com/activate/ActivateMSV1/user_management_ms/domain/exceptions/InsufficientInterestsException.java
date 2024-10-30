package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class InsufficientInterestsException extends RuntimeException {
    public InsufficientInterestsException() {
        super("Número de intereses insuficiente: El usuario debe tener al menos 3 intereses");
    }
}
