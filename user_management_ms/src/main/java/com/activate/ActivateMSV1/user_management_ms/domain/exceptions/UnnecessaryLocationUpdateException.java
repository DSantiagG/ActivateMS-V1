package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class UnnecessaryLocationUpdateException extends RuntimeException {
    public UnnecessaryLocationUpdateException() {
        super("La ubicación actual no ha cambiado. No es necesario actualizarla.");
    }}
