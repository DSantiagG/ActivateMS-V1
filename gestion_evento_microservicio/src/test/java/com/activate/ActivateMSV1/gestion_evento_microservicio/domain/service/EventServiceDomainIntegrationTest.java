package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.EventService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceDomainIntegrationTest {

    @Autowired
    private EventDomainService eventDomainService;
    private EventService eventService;
    private Event event;
    private Participant participant;
    private Organizer organizer;
    private User user;
    private User userOrg;

    @BeforeEach
    void setUp() throws Exception {
        eventDomainService = new EventDomainService();
        eventService = new EventService();
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.POLITICS);
        Location location = new Location(10L, 20L);

        user = new User(1L, "Juan", 25, "juan@gmail.com", interests, location);
        userOrg = new User(2L, "Org", 25, "org@gmail.com", interests, location);
        organizer = new Organizer(user);

        event = new Event(1L, 100, 120, "Test Event",
                "Event description", LocalDateTime.now().plusDays(1),
                location, EventType.PUBLIC, organizer, interests);

        participant = new Participant(1L, user);
    }

    @Test
    void testAddParticipant() {
        boolean result = eventDomainService.addParticipant(event, participant);

        assertTrue(result, "The participant should be added successfully.");
        assertTrue(event.getParticipants().contains(participant), "The event should contain the participant.");
    }
}