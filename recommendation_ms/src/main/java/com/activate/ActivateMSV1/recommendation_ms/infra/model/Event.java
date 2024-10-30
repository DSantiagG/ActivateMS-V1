package com.activate.ActivateMSV1.recommendation_ms.infra.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;

@Document("events")
@Data
public class Event {
    @Id
    private String id;
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
