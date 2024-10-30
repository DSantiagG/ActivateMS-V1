package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.EventService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.request.EvaluationRequest;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.EventType;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class EventControllerIntegrationTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private Long eventId;
    private State state;
    private EventType type;
    private int maxCapacity;
    private LocalDateTime date;
    private String comment;
    private int rating;
    private Long participantId;
    private Event event;
    private EvaluationRequest evaluationRequest;

    @BeforeEach
    void setUp() {
        eventId = 1L;
        state = State.OPEN;
        type = EventType.PUBLIC;
        maxCapacity = 100;
        date = LocalDateTime.now();
        comment = "Excellent event";
        rating = 5;
        participantId = 1L;
        event = new Event();
        evaluationRequest = new EvaluationRequest(comment, rating, participantId);
    }

    @Test
    void testGetEvent() {
        when(eventService.getEvent(eventId)).thenReturn(event);

        ResponseEntity<?> response = eventController.getEvent(eventId);

        assertEquals(ResponseEntity.ok(event), response);
        verify(eventService).getEvent(eventId);
    }

    @Test
    void testGetEvents() {
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        when(eventService.getEvents()).thenReturn(events);

        ResponseEntity<?> response = eventController.getEvents();

        assertEquals(ResponseEntity.ok(events), response);
        verify(eventService).getEvents();
    }

    @Test
    void testUpdateType() {
        doNothing().when(eventService).updateType(eventId);

        ResponseEntity<?> response = eventController.updateType(eventId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(eventService).updateType(eventId);
    }

    @Test
    void testUpdateMaxCapacity() {
        doNothing().when(eventService).updateMaxCapacity(eventId, maxCapacity);

        ResponseEntity<?> response = eventController.updateMaxCapacity(eventId, maxCapacity);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(eventService).updateMaxCapacity(eventId, maxCapacity);
    }

    @Test
    void testUpdateDate() {
        doNothing().when(eventService).updateDate(eventId, date);

        ResponseEntity<?> response = eventController.updateDate(eventId, date);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(eventService).updateDate(eventId, date);
    }

    @Test
    void testAddEvaluation() {
        doNothing().when(eventService).addEvaluation(eventId, comment, rating, participantId);

        ResponseEntity<?> response = eventController.addEvaluation(eventId, evaluationRequest);

        assertEquals(ResponseEntity.created(null).build(), response);
        verify(eventService).addEvaluation(eventId, comment, rating, participantId);
    }

    @Test
    void testAddParticipant() {
        doNothing().when(eventService).addParticipant(eventId, participantId);

        ResponseEntity<?> response = eventController.addParticipant(eventId, participantId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(eventService).addParticipant(eventId, participantId);
    }

    @Test
    void testRemoveParticipant() {
        doNothing().when(eventService).removeParticipant(eventId, participantId);

        ResponseEntity<?> response = eventController.removeParticipant(eventId, participantId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(eventService).removeParticipant(eventId, participantId);
    }
}