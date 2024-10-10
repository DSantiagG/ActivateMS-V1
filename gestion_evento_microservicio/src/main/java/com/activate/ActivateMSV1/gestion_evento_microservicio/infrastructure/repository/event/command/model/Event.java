package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int maxCapacity;
    private int duration; // In minutes
    private String name;
    private String description;
    private LocalDateTime date;
    private LocalDateTime lastModifiedDate;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name = "organizer_id")
    private Long organizer;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Interest.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "event_interests", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "interest")
    private Set<Interest> interests = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    @PreRemove
    protected void onDelete() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}