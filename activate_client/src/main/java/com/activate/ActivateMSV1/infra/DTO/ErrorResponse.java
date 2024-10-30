package com.activate.ActivateMSV1.infra.DTO;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int status;
}