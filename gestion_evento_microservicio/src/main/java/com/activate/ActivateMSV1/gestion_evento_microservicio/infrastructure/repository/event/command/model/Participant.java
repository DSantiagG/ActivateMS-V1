package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="participant")
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
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