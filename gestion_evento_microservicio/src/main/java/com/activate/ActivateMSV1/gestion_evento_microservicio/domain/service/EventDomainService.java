package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import org.springframework.stereotype.Service;

@Service
public class EventDomainService {

    public boolean addParticipant(Event event, Participant participant){
        EventInfo e = convertEventToInfo(event);
        if(!participant.isAvailable(e))
            throw new RuntimeException("The participant is already registered in an event at the same date and time: "+e.getDate().toString());
        if (event.addParticipant(participant)){
            participant.getParticipatedEvents().add(e);
            return true;
        }
        return false;
    }

    public EventInfo convertEventToInfo(Event event){
        return new EventInfo(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(), event.getDate(), event.getLocation(), event.getState(), event.getType(), event.getOrganizerName(), event.getInterests());
    }
}