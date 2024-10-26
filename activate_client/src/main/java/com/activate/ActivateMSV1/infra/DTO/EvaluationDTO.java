package com.activate.ActivateMSV1.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EvaluationDTO {
    private Long id;
    private String comment;
    private int score;
    private String author;
    private Long authorId;
}
