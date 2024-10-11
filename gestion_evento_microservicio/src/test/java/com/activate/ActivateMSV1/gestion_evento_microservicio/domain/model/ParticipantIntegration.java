package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParticipantIntegrationTest {

    private Participant participant;
    private User user;
    private EventInfo eventInfo;

    @BeforeEach
    void setUp() throws Exception {
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.TECHNOLOGY);
        Location location = new Location(10L, 20L);

        user = new User(1L, "Juan", 25, "juan@gmail.com", interests, location);

        eventInfo = new EventInfo(1L, 100, 120, "Test Event",
                "Event description", LocalDateTime.now().plusDays(1),
                location, State.OPEN, EventType.PUBLIC, "Organizer", interests);

        participant = new Participant(1L, user);
    }

    @Test
    void testParticipantCreation() {
        assertNotNull(participant);
        assertEquals(1L, participant.getId());
        assertEquals(user, participant.getUser());
        assertTrue(participant.getParticipatedEvents().isEmpty());
    }

    @Test
    void testIsAvailable() {
        assertTrue(participant.isAvailable(eventInfo));

        participant.getParticipatedEvents().add(eventInfo);
        assertFalse(participant.isAvailable(eventInfo));
    }
}