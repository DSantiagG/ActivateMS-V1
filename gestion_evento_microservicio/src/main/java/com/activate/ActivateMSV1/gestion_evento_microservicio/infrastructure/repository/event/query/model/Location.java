package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
}