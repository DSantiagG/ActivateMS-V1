package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OrganizerUnitTest {
    private Organizer organizer;
    private User user;
    private Event event;

    @BeforeEach
    void setUp() throws Exception {
        // Common setup for all tests
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.POLITICS);
        Location location = new Location(10L, 20L);

        user = new User(1L, "Juan", 25, "juan@gmail.com", interests, location);
        organizer = new Organizer(user);
        event = new Event(1L, 100, 120, "Test Event",
                "Event description", LocalDateTime.now().plusDays(1),
                location, EventType.PUBLIC, organizer, interests);
        organizer.createEvent(event);
    }

    @Test
    void testCreateEvent() {
        // Arrange
        Event newEvent = new Event(2L, 50, 90, "New Event",
                "New event description", LocalDateTime.now().plusDays(2),
                event.getLocation(), EventType.PRIVATE, organizer, event.getInterests());

        // Act
        boolean result = organizer.createEvent(newEvent);

        // Assert
        assertTrue(result, "The event should be created successfully.");
        assertEquals(2, organizer.getOrganizedEvents().size(), "The organizer should have 2 organized events.");
        assertEquals(newEvent, organizer.getOrganizedEvents().get(1), "The created event should be the new event.");
    }

    @Test
    void testCancelExistingEvent() throws Exception {
        // Act
        boolean result = organizer.cancelEvent(1L);

        // Assert
        assertTrue(result, "The event should be canceled successfully.");
        assertEquals(State.CANCELED, event.getState(), "The event state should be CANCELED.");
    }

    @Test
    void testCancelNonExistingEvent() throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            organizer.cancelEvent(2L);
        });

        assertEquals("Unable to cancel the event. Event not found", exception.getMessage());
    }

    @Test
    void testGetName() {
        String name = organizer.getName();

        assertEquals("Juan", name, "The organizer's name should be Juan.");
    }
}