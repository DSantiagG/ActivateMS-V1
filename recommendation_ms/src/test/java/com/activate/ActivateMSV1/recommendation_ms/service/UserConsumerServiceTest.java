package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserConsumerServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserConsumerService userConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage_UserIsNull() {
        userConsumerService.receiveMessage(null);

        verify(userRepository, never()).save(any());
    }

    @Test
    void testReceiveMessage_UserIsNotNull() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");

        userConsumerService.receiveMessage(userDTO);

        verify(userRepository, times(1)).save(any());
    }
}