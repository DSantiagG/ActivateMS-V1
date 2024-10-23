package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.OrganizerService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventType;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Interest;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Location;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.request.EventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrganizerControllerUnitTest {

    @Mock
    private OrganizerService organizerService;

    @InjectMocks
    private OrganizerController organizerController;

    private EventRequest eventRequest;

    @BeforeEach
    void setUp() {
        eventRequest = new EventRequest(100, 2, "Test Event", "Description of the test event", LocalDateTime.now(), 40.416775, -3.703790, EventType.PUBLIC, 1L, new HashSet<>(Set.of(Interest.MUSIC)));
    }

    @Test
    void testCreateEvent() throws Exception {
        ResponseEntity<?> response = organizerController.createEvent(eventRequest);
        assertEquals(ResponseEntity.created(null).build(), response);
        verify(organizerService).createEvent(eq(eventRequest.getMaxCapacity()), eq(eventRequest.getDuration()), eq(eventRequest.getName()), eq(eventRequest.getDescription()), eq(eventRequest.getDate()), any(Location.class), eq(eventRequest.getType()), eq(eventRequest.getOrganizerId()), eq(eventRequest.getInterests()));
    }

    @Test
    void testCancelEvent() throws Exception {
        ResponseEntity<?> response = organizerController.cancelEvent(1L, 1L);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(organizerService).cancelEvent(eq(1L), eq(1L));
    }
}