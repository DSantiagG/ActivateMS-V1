package com.activate.ActivateMSV1.infra.DTO;


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
    private EventTypeDTO type;
    private Long organizerId;
    private HashSet<InterestDTO> interests;

    public static EventRequest fromEventInfoDTO(EventInfoDTO event, Long organizerId) {
        return new EventRequest(
                event.getMaxCapacity(),
                event.getDuration(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getLocation().getLatitude(),
                event.getLocation().getLongitude(),
                event.getType(),
                organizerId,
                event.getInterests()
        );
    }
}