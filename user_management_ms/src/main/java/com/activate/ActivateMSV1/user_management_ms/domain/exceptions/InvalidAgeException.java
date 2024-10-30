package com.activate.ActivateMSV1.user_management_ms.domain.exceptions;

public class InvalidAgeException extends RuntimeException {
  public InvalidAgeException() {
    super("Edad no válida (Debes ser mayor de edad)");
  }
}
