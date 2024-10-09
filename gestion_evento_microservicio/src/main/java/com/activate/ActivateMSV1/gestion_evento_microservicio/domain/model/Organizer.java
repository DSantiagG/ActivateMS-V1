package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

public class Organizer {
    private User user;
    @Getter
    @Setter
    private ArrayList<Event> organizedEvents;

    public Organizer(User user) {
        this.user = user;
        this.organizedEvents = new ArrayList<>();
    }

    public boolean createEvent(Event event) {
        return organizedEvents.add(event);
    }

    public boolean cancelEvent(Long id) throws Exception {
        for (Event event : organizedEvents) {
            if (event.getId().equals(id)) {
                event.cancel();
                return true;
            }
        }
        throw new Exception("Unable to cancel the event. Event not found");
    }

    public String getName(){
        return user.getName();
    }
    public Long getId(){ return user.getId();}

}