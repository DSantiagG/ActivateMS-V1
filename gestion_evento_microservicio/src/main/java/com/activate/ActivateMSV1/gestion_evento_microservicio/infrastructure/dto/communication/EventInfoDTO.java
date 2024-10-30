package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class EventInfoDTO {
    private Long id;
    private int maxCapacity;
    private int duration; // In minutes
    private String name;
    private String description;
    private LocalDateTime date;
    private LocationDTO location;
    private StateDTO state;
    private EventTypeDTO type;
    private String organizerName;
    private HashSet<InterestDTO> interests;
}
