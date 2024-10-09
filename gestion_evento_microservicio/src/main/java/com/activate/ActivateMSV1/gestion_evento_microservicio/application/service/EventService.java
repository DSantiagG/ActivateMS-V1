package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services.UserService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Evaluation;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service.EventDomainService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.EventCommand;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.repository.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.repository.EventQueryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class EventService {

    @Autowired
    EventQueryRepository eventQueryRepository;
    @Autowired
    EventCommandRepository eventCommandRepository;
    @Autowired
    EventAdapter eventAdapter;

    @Autowired
    EventDomainService eventDomainService;

    @Autowired
    UserService userService;
    @Autowired
    ParticipantService participantService;

    public com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event getEvent(Long eventId) {
        return eventQueryRepository.findById(String.valueOf(eventId)).orElseThrow(() -> new RuntimeException("Event not found"));
    }
    public Event getEventDomain(Long eventId) throws Exception {
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event = eventQueryRepository.findById(String.valueOf(eventId)).orElseThrow(() -> new RuntimeException("Event not found"));
        return eventAdapter.mapEventToDomain(event);
    }
    public ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event> getEvents() throws Exception {
        return (ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event>) eventQueryRepository.findAll();
    }
    public ArrayList<Event> getEventsDomain() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event : eventQueryRepository.findAll()) {
            events.add(eventAdapter.mapEventToDomain(event));
        }
        return events;
    }

    public void updateType(Long eventId) throws Exception {
        Event event = getEventDomain(eventId);
        event.changeType();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    public void updateMaxCapacity(Long eventId, int maxCapacity) throws Exception {
        Event event = getEventDomain(eventId);
        event.updateMaxCapacity(maxCapacity);
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    public void updateDate(Long eventId, LocalDateTime date) throws Exception {
        Event event = getEventDomain(eventId);
        event.updateDate(date);
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    public void addEvaluation(Long eventId, String comment, int score, Long participantId) throws Exception {
        Event event = getEventDomain(eventId);
        User userParticipant = userService.getUser(participantId);
        Participant participant = new Participant(null, userParticipant);

        Evaluation evaluation = new Evaluation(null, comment, score, participant);
        event.addEvaluation(evaluation);

        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    @Transactional
    public void addParticipant(Long eventId, Long userParticipantId) throws Exception {
        Event event = getEventDomain(eventId);

        User userParticipantDomain = userService.getUser(userParticipantId);
        Participant participant = new Participant(null, userParticipantDomain);

        participant.getParticipatedEvents().addAll(participantService.getParticipantEvents(userParticipantId));

        if (eventDomainService.addParticipant(event, participant)){
            EventCommand eventCommand = eventAdapter.mapEventToInfrastructure(event);
            eventCommandRepository.save(eventCommand);
        }
    }
    @Transactional
    public void removeParticipant(Long eventId, Long participantId) throws Exception {
        Event event = getEventDomain(eventId);
        event.removeParticipant(participantId);
        eventCommandRepository.deleteParticipantFromEvent(eventId, participantId);
        eventCommandRepository.updateLastModifiedDate(eventId, LocalDateTime.now());
    }

    public void startEvent(Long eventId) throws Exception {
        Event event = getEventDomain(eventId);
        event.start();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    public void finishEvent(Long eventId) throws Exception {
        Event event = getEventDomain(eventId);
        event.finish();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }
}