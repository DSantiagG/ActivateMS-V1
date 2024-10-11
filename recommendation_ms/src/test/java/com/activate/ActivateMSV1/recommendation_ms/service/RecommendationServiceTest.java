package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.domain.*;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.exceptions.ServiceValidationException;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecommendationService recommendationService;

    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Location location = new Location(10,10);
        user = new User(1l, "John Doe", 25, "hola@gmail.com", new HashSet<>(), location);
        event = new Event(
                1L,
                100,
                120,
                "Tech Conference",
                "A conference about the latest in tech",
                LocalDateTime.of(2023, 12, 15, 10, 0),
                location,
                State.OPEN,
                EventType.PUBLIC,
                "Jane Smith",
                new HashSet<>());
    }

    @Test
    void testPair_UserNotFound() {
        when(userService.getUser(1L)).thenReturn(null);

        ServiceValidationException exception = assertThrows(ServiceValidationException.class, () -> {
            recommendationService.pair(1L);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testPair_Success() {
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        when(userService.getUser(1L)).thenReturn(UserMapper.INSTANCE.toRepoModelUser(user));
        when(eventService.getEvents()).thenReturn(Collections.singletonList(EventMapper.INSTANCE.toRepoModelEvent(event)));

        List<EventInfoDTO> result = recommendationService.pair(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(event.getName(), result.get(0).getName());
        verify(eventService, times(1)).getEvents();
    }

    @Test
    void testRecommendEvent_Success() {
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(UserMapper.INSTANCE.toRepoModelUser(user)));
        when(eventService.getEvent(1L)).thenReturn(EventMapper.INSTANCE.toRepoModelEvent(event));

        List<UserDTO> result = recommendationService.recommendEvent(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.get(0).getId());
        verify(eventService, times(1)).getEvent(1L);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetRecommendations_UserNotFound() {
        when(userService.getUser(1L)).thenReturn(null);

        ServiceValidationException exception = assertThrows(ServiceValidationException.class, () -> {
            recommendationService.getRecommendations(1L);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetRecommendations_Success() {
        user.getInterests().add(Interest.MUSIC);
        event.getInterests().add(Interest.MUSIC);

        when(userService.getUser(1L)).thenReturn(UserMapper.INSTANCE.toRepoModelUser(user));
        when(eventService.getEvents()).thenReturn(Collections.singletonList(EventMapper.INSTANCE.toRepoModelEvent(event)));

        recommendationService.pair(1L);
        List<EventInfoDTO> result = recommendationService.getRecommendations(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(event.getName(), result.get(0).getName());
        verify(userService, times(2)).getUser(1L);
    }
}