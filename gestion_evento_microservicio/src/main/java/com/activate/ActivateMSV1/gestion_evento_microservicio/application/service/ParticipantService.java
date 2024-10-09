package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services.UserService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service.EventDomainService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParticipantService {

    UserService userService;
    UserRepository userRepository;
    EventService eventService;
    EventDomainService eventDomainService;
    EventAdapter eventAdapter;

    @Autowired
    public ParticipantService(UserService userService, UserRepository userRepository, @Lazy EventService eventService, EventDomainService eventDomainService, EventAdapter eventAdapter) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.eventDomainService = eventDomainService;
        this.eventAdapter = eventAdapter;
    }

    public ArrayList<EventInfo> getParticipantEvents(Long participantId) throws Exception {

        User userParticipant = userRepository.findById(participantId).orElseThrow(() -> new RuntimeException("User not found"));

        ArrayList<EventInfo> participantEvents = new ArrayList<>();
        for(Participant p : userParticipant.getParticipants()) {
            participantEvents.add(eventDomainService.convertEventToInfo(eventAdapter.mapEventCommandToDomain(p.getEvent())));
        }

        return participantEvents;
    }
}