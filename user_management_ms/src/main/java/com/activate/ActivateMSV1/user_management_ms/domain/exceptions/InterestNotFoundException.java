package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class InterestNotFoundException extends RuntimeException {
    public InterestNotFoundException() {
        super("El interés seleccionado no se encuentra en entre tus intereses");
    }}
