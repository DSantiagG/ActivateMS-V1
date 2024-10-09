package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    private Long id;
    private String comment;
    private int score;
    private String author;
    private Long authorId;
}