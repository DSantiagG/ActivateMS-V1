package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto;

import lombok.Data;

@Data
public class EvaluationRequest {
    private String comment;
    private int score;
    private Long participantId;
}