package com.activate.ActivateMSV1.recommendation_ms.domain;

import com.activate.ActivateMSV1.recommendation_ms.infra.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationTest {

    private Recommendation recommendation;
    private User user;
    private Event event;
    private Location location;

    @BeforeEach
    void setUp() {
        recommendation = new Recommendation();
        location = new Location(10, 10);
        user = new User(1L, "John Doe", 30, "john.doe@example.com", new HashSet<>(), location);
        event = new Event(1L, 100, 120, "Concert", "Music concert", LocalDateTime.now(), location, State.OPEN, EventType.PUBLIC, "Organizer", new HashSet<>());
    }

    @Test
    void testPairUserToEvents() {
        ArrayList<Event> eventsAvailable = new ArrayList<>();
        ArrayList<Event> eventsRecommended = new ArrayList<>();
        eventsAvailable.add(event);
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        eventsRecommended = recommendation.recommendateEventsToUser(user, eventsAvailable);

        assertEquals(1, eventsRecommended.size());
        assertEquals(event, eventsRecommended.get(0));
    }

    @Test
    void testPairUserToEvents_NoMatchingEvents() {
        ArrayList<Event> eventsAvailable = new ArrayList<>();
        eventsAvailable.add(event);

        DomainException exception = assertThrows(DomainException.class, () -> {
            recommendation.recommendateEventsToUser(user, eventsAvailable);
        });

        assertEquals("There are no events available for the user", exception.getMessage());
    }

    @Test
    void testPairUserToEvents_NoMatchingEventsForInterests() {
        ArrayList<Event> eventsAvailable = new ArrayList<>();
        eventsAvailable.add(event);
        user.getInterests().add(Interest.SPORTS);
        event.getInterests().add(Interest.MUSIC);

        DomainException exception = assertThrows(DomainException.class, () -> {
            recommendation.recommendateEventsToUser(user, eventsAvailable);
        });

        assertEquals("There are no events available for the user", exception.getMessage());
    }

    @Test
    void testPairUserToEvents_NoMatchingEventsForLocation() {
        ArrayList<Event> eventsAvailable = new ArrayList<>();
        eventsAvailable.add(event);
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        user.setLocation(new Location(100, 100));
        event.setLocation(new Location(200, 200));

        DomainException exception = assertThrows(DomainException.class, () -> {
            recommendation.recommendateEventsToUser(user, eventsAvailable);
        });

        assertEquals("There are no events available for the user", exception.getMessage());
    }

    @Test
    void testRecommendUsersToEvent() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        ArrayList<User> recommendedUsers = recommendation.recommendUsersToEvent(event, users);

        assertEquals(1, recommendedUsers.size());
        assertEquals(user, recommendedUsers.get(0));
    }

    @Test
    void testRecommendUsersToEvent_NoMatchesForInterests() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.LITERATURE);

        ArrayList<User> recommendedUsers = recommendation.recommendUsersToEvent(event, users);

        assertEquals(0, recommendedUsers.size());
    }

    @Test
    void testRecommendUsersToEvent_NoMatchesForLocation() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        user.setLocation(new Location(100, 100));
        event.setLocation(new Location(200, 200));

        ArrayList<User> recommendedUsers = recommendation.recommendUsersToEvent(event, users);

        assertEquals(0, recommendedUsers.size());
    }

}