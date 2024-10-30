package com.activate.ActivateMSV1.recommendation_ms.infra.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
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
