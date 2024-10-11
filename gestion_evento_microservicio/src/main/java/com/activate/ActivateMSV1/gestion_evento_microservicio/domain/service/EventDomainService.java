package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.DomainException;
import org.springframework.stereotype.Service;

@Service
public class EventDomainService {

    /**
     * Add a participant to an event
     * @param event event to add the participant
     * @param participant participant to add to the event
     * @return true if the participant was added to the event, false otherwise
     */
    public boolean addParticipant(Event event, Participant participant){
        EventInfo e = mapEventToEventInfo(event);
        if(!participant.isAvailable(e))
            throw new DomainException("The participant is already registered in an event at the same date and time: "+e.getDate().toString());
        if (event.addParticipant(participant)){
            participant.getParticipatedEvents().add(e);
            return true;
        }
        return false;
    }

    /**
     * Map an event to an event info
     * @param event event to map
     * @return event info
     */
    private EventInfo mapEventToEventInfo(Event event){
        return new EventInfo(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(), event.getDate(), event.getLocation(), event.getState(), event.getType(), event.getOrganizerName(), event.getInterests());
    }
}