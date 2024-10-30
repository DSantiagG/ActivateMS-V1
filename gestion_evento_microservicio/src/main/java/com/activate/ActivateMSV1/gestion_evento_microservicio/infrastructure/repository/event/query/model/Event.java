package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private Organizer organizer;
    private Set<Interest> interests;
    private List<Participant> participants;
    private List<Evaluation> evaluations;
}