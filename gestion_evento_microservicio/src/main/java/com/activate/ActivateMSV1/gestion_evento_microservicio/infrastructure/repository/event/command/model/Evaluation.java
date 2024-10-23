package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private int score;
    private Long author;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    @PrePersist
    public void prePersist() {
        if (event != null) {
            event.setLastModifiedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (event != null) {
            event.setLastModifiedDate(LocalDateTime.now());
        }
    }

    @PreRemove
    public void preRemove() {
        if (event != null) {
            event.setLastModifiedDate(LocalDateTime.now());
        }
    }

}