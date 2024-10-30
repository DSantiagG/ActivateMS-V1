package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.NotificationDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EventConsumerServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private EventConsumerService eventConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage_EventAlreadyExists() {
        EventInfoDTO eventInfoDTO = new EventInfoDTO();
        eventInfoDTO.setId(1L);
        when(eventRepository.findById("1")).thenReturn(Optional.of(EventMapper.INSTANCE.toRepoModelEvent(eventInfoDTO)));

        eventConsumerService.receiveMessage(eventInfoDTO);

        verify(eventRepository, times(1)).save(any());
        verify(recommendationService, never()).recommendUsersToEvent(anyLong());
    }

    @Test
    void testReceiveMessage_NewEvent() {
        EventInfoDTO eventInfoDTO = new EventInfoDTO();
        UserDTO userDTO = new UserDTO();
        NotificationDTO notificationDTO = new NotificationDTO();

        userDTO.setId(1L);
        userDTO.setName("John Doe");

        eventInfoDTO.setId(1L);
        eventInfoDTO.setName("Tech Conference");
        eventInfoDTO.setDate(LocalDateTime.now());

        when(eventRepository.findById("1")).thenReturn(Optional.empty());
        when(recommendationService.recommendUsersToEvent(1L)).thenReturn(new ArrayList<>(List.of(userDTO)));
        eventConsumerService.receiveMessage(eventInfoDTO);

        verify(eventRepository, times(1)).save(any());
        verify(recommendationService, times(1)).recommendUsersToEvent(1L);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("notificationQueue"), any(NotificationDTO.class));
    }
}