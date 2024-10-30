package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class InterestAlreadyAddedException extends RuntimeException {
    public InterestAlreadyAddedException() {
        super("El interés ya ha sido agregado previamente");
    }
}
