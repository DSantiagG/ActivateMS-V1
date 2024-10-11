package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.EventService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.request.EvaluationRequest;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventControllerUnitTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateType() throws Exception {
        Long eventId = 1L;
        EventType type = EventType.PUBLIC;

        eventController.updateType(eventId);

        verify(eventService).updateType(eq(eventId));
    }

    @Test
    void testUpdateMaxCapacity() throws Exception {
        Long eventId = 1L;
        int maxCapacity = 100;

        eventController.updateMaxCapacity(eventId, maxCapacity);
        verify(eventService).updateMaxCapacity(eq(eventId), eq(maxCapacity));
    }

    @Test
    void testUpdateDate() throws Exception {
        Long eventId = 1L;
        LocalDateTime date = LocalDateTime.now();

        eventController.updateDate(eventId, date);
        verify(eventService).updateDate(eq(eventId), eq(date));
    }

    @Test
    void testAddEvaluation() throws Exception {
        Long eventId = 1L;
        String comment = "Excellent event";
        int rating = 5;
        Long participantId = 1L;

        EvaluationRequest request = new EvaluationRequest(comment, rating, participantId);
        eventController.addEvaluation(eventId, request);
        verify(eventService).addEvaluation(eq(eventId), eq(comment), eq(rating), eq(participantId));
    }

    @Test
    void testAddParticipant() throws Exception {
        Long eventId = 1L;
        Long participantId = 1L;

        eventController.addParticipant(eventId, participantId);
        verify(eventService).addParticipant(eq(eventId), eq(participantId));
    }

    @Test
    void testRemoveParticipant() throws Exception {
        Long eventId = 1L;
        Long participantId = 1L;

        eventController.removeParticipant(eventId, participantId);
        verify(eventService).removeParticipant(eq(eventId), eq(participantId));
    }
}