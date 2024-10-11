package com.activate.ActivateMSV1.recommendation_ms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class Event {
    private Long id;
    private int maxCapacity;
    private int duration; // In minutes
    private String name;
    private String description;
    private LocalDateTime date;
    private Location location;
    private State state;
    private EventType type;
    private String organizerName;
    private HashSet<Interest> interests;
}
