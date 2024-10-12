package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("El email ingresado no es valido");
    }
}
