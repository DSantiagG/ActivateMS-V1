package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.EventService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class EventController {
    /**
     * Application layer object
     */
    @Autowired
    private EventService eventService;

    public Event getEvent(Long eventId) throws Exception {
        return eventService.getEvent(eventId);
    }

    public ArrayList<Event> getEvents() throws Exception {
        return eventService.getEvents();
    }

    public void updateType(Long eventId) throws Exception {
        eventService.updateType(eventId);
    }

    public void updateMaxCapacity(Long eventId, int maxCapacity) throws Exception {
        eventService.updateMaxCapacity(eventId, maxCapacity);
    }

    public void updateDate(Long eventId, LocalDateTime date) throws Exception {
        eventService.updateDate(eventId, date);
    }

    public void addEvaluation(Long eventId, String comment, int score, Long participantId) throws Exception {
        eventService.addEvaluation(eventId, comment, score, participantId);
    }

    public void addParticipant(Long eventId, Long participantId) throws Exception {
        eventService.addParticipant(eventId, participantId);
    }

    public void removeParticipant(Long eventId, Long participantId) throws Exception {
        eventService.removeParticipant(eventId, participantId);
    }

    public void startEvent(Long eventId) throws Exception {
        eventService.startEvent(eventId);
    }

    public void endEvent(Long eventId) throws Exception {
        eventService.finishEvent(eventId);
    }
}