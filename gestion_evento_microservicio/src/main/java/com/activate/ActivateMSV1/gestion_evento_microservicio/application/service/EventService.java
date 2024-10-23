package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Evaluation;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service.EventDomainService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.NotFoundException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.EventAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.EventQueryRepository;
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
    EventPublisherService eventPublisherService;

    @Autowired
    EventDomainService eventDomainService;

    @Autowired
    UserService userService;
    @Autowired
    ParticipantService participantService;

    /**
     * Get event by id
     * @param eventId id of the event
     * @return Query event
     */
    public com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event getEvent(Long eventId) {
        return eventQueryRepository.findById(String.valueOf(eventId)).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    /**
     * Get event by id
     * @param eventId id of the event
     * @return Domain event
     */
    public com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event getEventDomain(Long eventId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event = eventQueryRepository.findById(String.valueOf(eventId)).orElseThrow(() -> new NotFoundException("Event not found"));
        return eventAdapter.mapEventToDomain(event);
    }

    /**
     * Get all events
     * @return Query events
     */
    public ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event> getEvents() {
        return (ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event>) eventQueryRepository.findAll();
    }

    /**
     * Get all events
     * @return Domain events
     */
    public ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event> getEventsDomain() {
        ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event> events = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event : eventQueryRepository.findAll()) {
            events.add(eventAdapter.mapEventToDomain(event));
        }
        return events;
    }

    /**
     * Update event type
     * @param eventId id of the event
     */
    public void updateType(Long eventId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.changeType();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
        eventPublisherService.publishEvent(event);
    }

    /**
     * Update max capacity
     * @param eventId id of the event
     * @param maxCapacity new max capacity
     */
    public void updateMaxCapacity(Long eventId, int maxCapacity) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.updateMaxCapacity(maxCapacity);
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
        eventPublisherService.publishEvent(event);
    }

    /**
     * Update date
     * @param eventId id of the event
     * @param date new date
     */
    public void updateDate(Long eventId, LocalDateTime date) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.updateDate(date);
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
        eventPublisherService.publishNotifications(event, "One of your events has been updated","The date of the event " + event.getName() + " has been updated to " + date);
        eventPublisherService.publishEvent(event);
    }

    /**
     * Add evaluation to event
     * @param eventId id of the event
     * @param comment comment of the evaluation
     * @param score score of the evaluation
     * @param participantId id of the participant
     */
    public void addEvaluation(Long eventId, String comment, int score, Long participantId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        User userParticipant = userService.getUser(participantId);
        Participant participant = new Participant(null, userParticipant);

        Evaluation evaluation = new Evaluation(null, comment, score, participant);
        event.addEvaluation(evaluation);

        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
    }

    /**
     * Add participant to event
     * @param eventId id of the event
     * @param userParticipantId id of the participant
     */
    @Transactional
    public void addParticipant(Long eventId, Long userParticipantId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);

        User userParticipantDomain = userService.getUser(userParticipantId);
        Participant participant = new Participant(null, userParticipantDomain);

        participant.getParticipatedEvents().addAll(participantService.getParticipantEvents(userParticipantId));

        if (eventDomainService.addParticipant(event, participant)){
            Event eventCommand = eventAdapter.mapEventToInfrastructure(event);
            eventCommandRepository.save(eventCommand);
            eventPublisherService.publishEvent(event);
        }
    }

    /**
     * Remove participant from event
     * @param eventId id of the event
     * @param participantId id of the participant
     */
    @Transactional
    public void removeParticipant(Long eventId, Long participantId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.removeParticipant(participantId);
        eventCommandRepository.deleteParticipantFromEvent(eventId, participantId);
        eventCommandRepository.updateLastModifiedDate(eventId, LocalDateTime.now());
        eventPublisherService.publishEvent(event);
    }

    /**
     * Start event
     * @param eventId id of the event
     */
    public void startEvent(Long eventId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.start();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
        eventPublisherService.publishEvent(event);
    }

    /**
     * Finish event
     * @param eventId id of the event
     */
    public void finishEvent(Long eventId) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = getEventDomain(eventId);
        event.finish();
        eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(event));
        eventPublisherService.publishEvent(event);
    }
}