package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.DomainException;
import lombok.Data;

@Data
public class Evaluation {
    private Long id;
    private String comment;
    private int score;
    private Participant author;

    public Evaluation(Long id, String comment, int score, Participant author) {
        if(score < 0 || score > 5)
            throw new DomainException("The score of the evaluation must be between 0 and 5");
        if(comment == null || comment.isEmpty())
            throw new DomainException("The comment of the evaluation cannot be null or empty");
        if(author == null)
            throw new DomainException("The author of the evaluation cannot be null");
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.author = author;
    }
}