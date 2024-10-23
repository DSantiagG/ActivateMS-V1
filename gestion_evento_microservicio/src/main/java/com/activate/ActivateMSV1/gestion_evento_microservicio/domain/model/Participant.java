package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Getter
public class Participant {
    private Long id;
    @Getter
    private User user;
    @Setter
    @Getter
    private ArrayList<EventInfo> participatedEvents;

    public Participant(Long id, User user) {
        this.id = id;
        this.user = user;
        this.participatedEvents = new ArrayList<>();
    }

    public boolean isAvailable(EventInfo event) {
        for (EventInfo e : participatedEvents) {
            if (e.getDate().truncatedTo(ChronoUnit.MINUTES).equals(event.getDate().truncatedTo(ChronoUnit.MINUTES)))  return false;
            if (e.getDate().isAfter(event.getDate()) && e.getDate().isBefore(event.getDate().plusMinutes(event.getDuration()))) return false;
            if (event.getDate().isAfter(e.getDate()) && event.getDate().isBefore(e.getDate().plusMinutes(e.getDuration()))) return false;
        }
        return true;
    }
}