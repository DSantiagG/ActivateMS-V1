package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.ParticipantService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantControllerUnitTest {

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ParticipantController participantController;

    private Long participantId;
    private ArrayList<EventInfo> events;

    @BeforeEach
    void setUp() {
        participantId = 1L;
        events = new ArrayList<>();
        events.add(new EventInfo());
    }

    @Test
    void testGetParticipantEvents() {
        when(participantService.getParticipantEvents(participantId)).thenReturn(events);

        ResponseEntity<?> response = participantController.getParticipantEvents(participantId);
        assertEquals(ResponseEntity.ok(events), response);
        verify(participantService).getParticipantEvents(eq(participantId));
    }

    @Test
    void testGetParticipantEventsNotFound() {
        when(participantService.getParticipantEvents(participantId)).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = participantController.getParticipantEvents(participantId);
        assertEquals(ResponseEntity.notFound().build(), response);
        verify(participantService).getParticipantEvents(eq(participantId));
    }
}