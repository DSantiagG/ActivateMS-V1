package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
public class EventInfo {
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

    public EventInfo(Long id, int maxCapacity, int duration, String name, String description, LocalDateTime date, Location location, State state, EventType type, String organizerName, HashSet<Interest> interests) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.duration = duration;
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.state = state;
        this.type = type;
        this.organizerName = organizerName;
        this.interests = interests;
    }

    public EventInfo() {

    }
}