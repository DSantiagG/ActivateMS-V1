package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EventUnitTest {

    private Event event;
    private Organizer organizer;
    private User user;
    private Participant participant;
    private Participant participant2;
    private Evaluation evaluation;

    @BeforeEach
    void setUp() throws Exception {
        HashSet<Interest> interests = new HashSet<>();
        interests.add(Interest.CINEMA);
        interests.add(Interest.MUSIC);
        interests.add(Interest.POLITICS);
        Location location = new Location(10L, 20L);
        LocalDateTime date = LocalDateTime.now().plusDays(1);

        user = new User(1L, "Juan", 25, "juan@gmail.com", interests, location);
        organizer = new Organizer(user);
        participant = new Participant(1L, user);
        participant2 = new Participant(2L, new User(2L, "Ana", 22, "ana@gmail.com", interests, new Location(30L, 40L)));
        event = new Event(1L, 100, 120, "Yoga Workshop", "Relaxing event", date, location, EventType.PUBLIC, organizer, interests);
        evaluation = new Evaluation(1L, "Excellent", 5, participant);
    }

    @Test
    void testValidEventConstructor() {
        assertNotNull(event);
        assertEquals(100, event.getMaxCapacity());
        assertEquals(120, event.getDuration());
        assertEquals("Yoga Workshop", event.getName());
        assertEquals("Relaxing event", event.getDescription());
        assertEquals(LocalDateTime.now().plusDays(1).toLocalDate(), event.getDate().toLocalDate());
        assertEquals(new Location(10L, 20L), event.getLocation());
        assertEquals(EventType.PUBLIC, event.getType());
        // Verify that interests are present, regardless of order
        assertTrue(event.getInterests().contains(Interest.CINEMA));
        assertTrue(event.getInterests().contains(Interest.MUSIC));
        assertTrue(event.getInterests().contains(Interest.POLITICS));
    }

    @Test
    void testEventConstructorZeroDuration() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 0, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testEventConstructorDurationMoreThanOneDay() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 1441, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testEventConstructorPastDate() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 120, "Yoga Workshop", "Relaxing event", LocalDateTime.now().minusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testEventConstructorZeroMaxCapacity() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 0, 120, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testEventConstructorNullLocation() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 120, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), null, EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testEventConstructorNullInterests() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 120, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, null));
    }

    @Test
    void testEventConstructorEmptyInterests() {
        assertThrows(RuntimeException.class, () -> new Event(1L, 100, 120, "Yoga Workshop", "Relaxing event", LocalDateTime.now().plusDays(1), new Location(10L, 20L), EventType.PUBLIC, organizer, new HashSet<>()));
    }

    @Test
    void testCancelFinishedEvent() {
        event.setState(State.IN_PROGRESS);
        event.finish();
        assertThrows(RuntimeException.class, () -> event.cancel());
    }

    @Test
    void testCancelEvent() {
        event.cancel();
        assertEquals(State.CANCELED, event.getState());
    }

    @Test
    void testFinishEvent() {
        event.start();
        event.finish();
        assertEquals(State.FINISHED, event.getState());
    }

    @Test
    void testStartEventNotOpenOrClosed() {
        event.start();
        event.finish();
        assertThrows(RuntimeException.class, () -> event.start());
    }

    @Test
    void testStartEvent() {
        event.start();
        assertEquals(State.IN_PROGRESS, event.getState());
    }

    @Test
    void testChangeEventTypeToPublic() {
        event.changeType();
        assertEquals(EventType.PRIVATE, event.getType());
    }

    @Test
    void testChangeEventTypeToPrivate() {
        event.changeType();
        event.changeType();
        assertEquals(EventType.PUBLIC, event.getType());
    }

    @Test
    void testChangeEventTypeFromPrivateToPublic() {
        event.changeType();
        event.changeType();
        assertEquals(EventType.PUBLIC, event.getType());
    }

    @Test
    void testUpdateMaxCapacityLessThanZero() {
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(-1));
    }

    @Test
    void testUpdateMaxCapacityFinishedEvent() {
        event.setState(State.FINISHED);
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(200));
    }

    @Test
    void testUpdateMaxCapacityCanceledEvent() {
        event.cancel();
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(200));
    }

    @Test
    void testUpdateMaxCapacityExceededEvent() {
        event.addParticipant(participant);
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(0));
    }

    @Test
    void testUpdateMaxCapacity() {
        event.updateMaxCapacity(200);
        assertEquals(200, event.getMaxCapacity());
    }

    @Test
    void testUpdateMaxCapacityParticipantsExceedNewCapacity() {
        event.addParticipant(participant);
        event.addParticipant(participant2);
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(1));
    }

    @Test
    void testUpdateDateFinishedEvent() {
        event.setState(State.FINISHED);
        assertThrows(RuntimeException.class, () -> event.updateDate(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testUpdateDateCanceledEvent() {
        event.cancel();
        assertThrows(RuntimeException.class, () -> event.updateDate(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testUpdateDate() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);
        event.updateDate(newDate);
        assertEquals(newDate, event.getDate());
    }

    @Test
    void testAddParticipantFinishedEvent() throws Exception {
        event.setState(State.FINISHED);
        assertThrows(RuntimeException.class, () -> event.addParticipant(participant));
    }

    @Test
    void testAddParticipantCanceledEvent() throws Exception {
        event.cancel();
        assertThrows(RuntimeException.class, () -> event.addParticipant(participant));
    }

    @Test
    void testAddParticipant() throws Exception {
        event.addParticipant(participant);
        assertTrue(event.getParticipants().contains(participant));
    }

    @Test
    void testRemoveParticipantFinishedEvent() throws Exception {
        event.setState(State.FINISHED);
        assertThrows(RuntimeException.class, () -> event.removeParticipant(participant.getId()));
    }

    @Test
    void testRemoveParticipantCanceledEvent() throws Exception {
        event.cancel();
        assertThrows(RuntimeException.class, () -> event.removeParticipant(participant.getId()));
    }

    @Test
    void testAddAlreadyRegisteredParticipant() {
        event.addParticipant(participant);
        assertThrows(RuntimeException.class, () -> event.addParticipant(participant), "The participant is already registered in the event");
    }

    @Test
    void testAddParticipantMaxCapacity() {
        event.updateMaxCapacity(2);
        event.addParticipant(participant);
        event.addParticipant(participant2);
        assertEquals(State.CLOSED, event.getState());
    }

    @Test
    void testUpdateMaxCapacityExceedsParticipants() {
        event.addParticipant(participant);
        assertThrows(RuntimeException.class, () -> event.updateMaxCapacity(0), "Cannot update the capacity of an event that has already exceeded the new capacity");
    }

    @Test
    void testUpdateValidMaxCapacity() {
        event.addParticipant(participant);
        assertDoesNotThrow(() -> event.updateMaxCapacity(2));
        assertEquals(2, event.getMaxCapacity());
    }

    @Test
    void testRemoveParticipant() {
        event.addParticipant(participant);
        event.removeParticipant(participant.getId());
        assertFalse(event.getParticipants().contains(participant));
    }

    @Test
    void testRemoveParticipantNotOpenOrClosed() {
        // Set the event state to a value other than OPEN or CLOSED
        event.setState(State.CANCELED);

        // Attempt to remove a participant and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> event.removeParticipant(participant.getId()));
        assertEquals("Cannot remove a participant from an event that is not open or closed", exception.getMessage());
    }

    @Test
    void removeParticipant_closedStateShouldRemove() {
        event.setState(State.CLOSED);
        event.getParticipants().add(participant);
        assertTrue(event.removeParticipant(participant.getId()));
        assertFalse(event.getParticipants().contains(participant));
    }

    @Test
    void testRemoveNonRegisteredParticipant() {
        event.setState(State.OPEN);
        assertThrows(RuntimeException.class, () -> event.removeParticipant(participant.getId()), "The participant is not registered in the event");
    }

    @Test
    void testRemoveRegisteredParticipant() {
        event.setState(State.OPEN);
        event.addParticipant(participant);
        assertDoesNotThrow(() -> event.removeParticipant(participant.getId()));
        assertFalse(event.getParticipants().contains(participant));
    }

    @Test
    void testAddEvaluationFinishedEvent() throws Exception {
        event.setState(State.FINISHED);
        assertThrows(RuntimeException.class, () -> event.addEvaluation(evaluation));
    }

    @Test
    void testAddEvaluationNonRegisteredParticipant() throws Exception {
        assertThrows(RuntimeException.class, () -> event.addEvaluation(evaluation));
    }

    @Test
    void testAddEvaluation() throws Exception {
        event.addParticipant(participant);
        event.setState(State.FINISHED);
        event.addEvaluation(evaluation);
        assertTrue(event.getEvaluations().contains(evaluation));
    }

    @Test
    void testAddEvaluationNotFinishedEvent() throws Exception {
        event.addParticipant(participant);
        assertThrows(RuntimeException.class, () -> event.addEvaluation(evaluation));
    }

    @Test
    void testAddEvaluationNonParticipant() throws Exception {
        assertThrows(RuntimeException.class, () -> event.addEvaluation(evaluation));
    }

    @Test
    void testUpdateDateLessThanTwelveHours() {
        LocalDateTime newDate = LocalDateTime.now().plusHours(11);
        assertThrows(RuntimeException.class, () -> event.updateDate(newDate));
    }

    @Test
    void testUpdateValidDate() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);
        event.updateDate(newDate);
        assertEquals(newDate, event.getDate());
    }

    @Test
    void testUpdatePastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        assertThrows(RuntimeException.class, () -> event.updateDate(pastDate));
    }

    @Test
    void testOrganizerName() {
        assertEquals("Juan", event.getOrganizerName());
    }

    @Test
    void testStartEventStateNotOpenOrClosed() {
        event.setState(State.CANCELED);
        assertThrows(RuntimeException.class, () -> event.start());
    }

    @Test
    void testStartEventStateOpen() {
        event.setState(State.OPEN);
        assertDoesNotThrow(() -> event.start());
        assertEquals(State.IN_PROGRESS, event.getState());
    }

    @Test
    void testStartEventStateClosed() {
        event.setState(State.CLOSED);
        assertDoesNotThrow(() -> event.start());
        assertEquals(State.IN_PROGRESS, event.getState());
    }

    @Test
    void testUpdateDateInProgressEvent() {
        event.setState(State.IN_PROGRESS);
        LocalDateTime newDate = LocalDateTime.now().plusDays(2);
        assertThrows(RuntimeException.class, () -> event.updateDate(newDate), "Cannot update the date of an event that has already started");
    }

}