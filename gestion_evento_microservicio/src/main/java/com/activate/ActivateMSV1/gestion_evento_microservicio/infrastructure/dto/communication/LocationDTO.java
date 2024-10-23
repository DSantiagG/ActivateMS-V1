package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {
    private double latitude;
    private double longitude;
}
