package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class CannotRemoveInterestException extends RuntimeException {
    public CannotRemoveInterestException() {
        super("No se puede eliminar el interés. Debes tener al menos tres intereses registrados");
    }
}
