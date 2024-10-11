package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.DomainException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.EventAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventDomainService {

    @Autowired
    EventAdapter eventAdapter;

    public boolean addParticipant(Event event, Participant participant){
        EventInfo e = eventAdapter.mapEventToEventInfo(event);
        if(!participant.isAvailable(e))
            throw new DomainException("The participant is already registered in an event at the same date and time: "+e.getDate().toString());
        if (event.addParticipant(participant)){
            participant.getParticipatedEvents().add(e);
            return true;
        }
        return false;
    }
}