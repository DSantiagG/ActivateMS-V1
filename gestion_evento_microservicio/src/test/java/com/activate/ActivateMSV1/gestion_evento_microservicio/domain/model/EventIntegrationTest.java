package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventIntegrationTest {

    private Event event;
    private Location location;
    private Organizer organizer;
    private HashSet<Interest> interests;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        location = new Location(40L, -3L);
        user = new User(1L, "Juan", 25, "juan@gmail.com", interests, location);
        organizer = new Organizer(user);
        interests = new HashSet<>();
        interests.add(Interest.MUSIC);
        interests.add(Interest.CINEMA);
        interests.add(Interest.POLITICS);
        event = new Event(1L, 100, 120, "Concert", "Rock concert", LocalDateTime.now().plusDays(1), location, EventType.PUBLIC, organizer, interests);
    }

    @Test
    void testEventCreation() {
        assertNotNull(event);
        assertEquals(1L, event.getId());
        assertEquals(100, event.getMaxCapacity());
        assertEquals(120, event.getDuration());
        assertEquals("Concert", event.getName());
        assertEquals("Rock concert", event.getDescription());
        assertEquals(location, event.getLocation());
        assertEquals(State.OPEN, event.getState());
        assertEquals(EventType.PUBLIC, event.getType());
        assertEquals(organizer, event.getOrganizer());
        assertEquals(interests, event.getInterests());
    }

    @Test
    void testCancel() {
        event.cancel();
        assertEquals(State.CANCELED, event.getState());
    }

    @Test
    void testFinish() {
        event.start();
        event.finish();
        assertEquals(State.FINISHED, event.getState());
    }

    @Test
    void testStart() {
        event.start();
        assertEquals(State.IN_PROGRESS, event.getState());
    }

    @Test
    void testChangeType() {
        event.changeType();
        assertEquals(EventType.PRIVATE, event.getType());
    }

    @Test
    void testUpdateMaxCapacity() {
        boolean result = event.updateMaxCapacity(150);
        assertTrue(result);
        assertEquals(150, event.getMaxCapacity());
    }

    @Test
    void testUpdateDate() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);
        event.updateDate(newDate);
        assertEquals(newDate, event.getDate());
    }

    @Test
    void testAddEvaluation() {
        Participant author = new Participant(1L, user);
        event.addParticipant(author);
        event.start();
        event.finish();
        Evaluation evaluation = new Evaluation(1L, "Good event", 4, author);
        event.addEvaluation(evaluation);
        assertTrue(event.getEvaluations().contains(evaluation));
    }

    @Test
    void testAddParticipant() {
        Participant participant = new Participant(1L, user);
        boolean result = event.addParticipant(participant);
        assertTrue(result);
        assertTrue(event.getParticipants().contains(participant));
    }

    @Test
    void testRemoveParticipant() {
        Participant participant = new Participant(1L, user);
        event.addParticipant(participant);
        boolean result = event.removeParticipant(participant.getId());
        assertTrue(result);
        assertFalse(event.getParticipants().contains(participant));
    }
}