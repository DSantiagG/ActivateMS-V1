package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaluationRequest {
    private String comment;
    private int score;
    private Long participantId;
}
