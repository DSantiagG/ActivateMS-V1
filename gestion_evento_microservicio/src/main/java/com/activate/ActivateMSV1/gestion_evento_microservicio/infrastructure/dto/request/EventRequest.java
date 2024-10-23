package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.request;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Interest;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class EventRequest {
    private int maxCapacity;
    private int duration;
    private String name;
    private String description;
    private LocalDateTime date;
    private Double latitude;
    private Double longitude;
    private EventType type;
    private Long organizerId;
    private HashSet<Interest> interests;
}