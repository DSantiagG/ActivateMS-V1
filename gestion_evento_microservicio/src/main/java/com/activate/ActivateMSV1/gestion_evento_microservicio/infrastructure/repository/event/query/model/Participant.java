package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    private Long id;
    private Long userId;
    private String name;
}