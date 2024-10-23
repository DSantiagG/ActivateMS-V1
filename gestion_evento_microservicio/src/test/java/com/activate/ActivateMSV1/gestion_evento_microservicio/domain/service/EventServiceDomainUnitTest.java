package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service;


import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceDomainUnitTest {

    @InjectMocks
    private EventDomainService eventDomainService;

    @Mock
    private Event event;

    @Mock
    private Participant participant;

    private EventInfo eventInfo;

    @BeforeEach
    void setUp() {
        LocalDateTime date = LocalDateTime.now();
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.POLITICS);
        eventInfo = new EventInfo(1L, 10, 60, "Event1", "Description", date, new Location(40L, -3L), State.OPEN, EventType.PUBLIC, "John", interests);
    }

    @Test
    void addParticipant_participantAvailable() {
        when(participant.isAvailable(any(EventInfo.class))).thenReturn(true);
        when(event.addParticipant(participant)).thenReturn(true);

        boolean result = eventDomainService.addParticipant(event, participant);

        assertTrue(result);
        verify(participant).isAvailable(any(EventInfo.class));
        verify(event).addParticipant(participant);
    }

    @Test
    void addParticipant_participantNotAvailable() {
        // Ensure eventInfo has a valid date
        LocalDateTime date = LocalDateTime.now();
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.POLITICS);
        eventInfo = new EventInfo(1L, 10, 60, "Event1", "Description", date, new Location(40L, -3L), State.OPEN, EventType.PUBLIC, "John", interests);

        when(participant.isAvailable(any(EventInfo.class))).thenReturn(false);
        when(event.getDate()).thenReturn(date);  // Set the date in the event mock as well

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventDomainService.addParticipant(event, participant);
        });

        // Verify that the exception message is correct with the valid date
        assertEquals("The participant is already registered in an event at the same date and time: " + eventInfo.getDate().toString(), exception.getMessage());

        verify(participant).isAvailable(any(EventInfo.class));
        verify(event, never()).addParticipant(participant);
    }
}