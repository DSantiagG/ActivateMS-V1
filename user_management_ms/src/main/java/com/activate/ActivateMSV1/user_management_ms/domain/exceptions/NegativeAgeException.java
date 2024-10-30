package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class NegativeAgeException extends RuntimeException {

    public NegativeAgeException() {
        super("Edad no valida ,la edad no puede ser negativa");
    }
}

