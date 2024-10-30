package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.model.Event;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEvent() {
        Event event = new Event();
        event.setId("1");
        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        Event result = eventService.getEvent(1L);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(eventRepository, times(1)).findById("1");
    }

    @Test
    void testGetEvent_NotFound() {
        when(eventRepository.findById("1")).thenReturn(Optional.empty());

        Event result = eventService.getEvent(1L);

        assertNull(result);
        verify(eventRepository, times(1)).findById("1");
    }

    @Test
    void testGetEvents() {
        Event event1 = new Event();
        event1.setId("1");
        Event event2 = new Event();
        event2.setId("2");
        List<Event> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getEvents();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testSaveEvent() {
        Event event = new Event();
        event.setId("1");
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.saveEvent(event);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(eventRepository, times(1)).save(event);
    }
}