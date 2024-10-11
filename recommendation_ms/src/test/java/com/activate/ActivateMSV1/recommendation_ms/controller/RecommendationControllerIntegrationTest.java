package com.activate.ActivateMSV1.recommendation_ms.controller;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.LocationDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.StateDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventTypeDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.InterestDTO;
import com.activate.ActivateMSV1.recommendation_ms.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    public void testPairUserWithFullEventInfo() throws Exception {
        Long userId = 1L;

        // Crear datos simulados para el evento
        EventInfoDTO mockEventInfo = new EventInfoDTO();
        mockEventInfo.setId(1L);
        mockEventInfo.setMaxCapacity(100);
        mockEventInfo.setDuration(120);  // 120 minutos
        mockEventInfo.setName("Tech Conference");
        mockEventInfo.setDescription("A conference about the latest in tech");
        mockEventInfo.setDate(LocalDateTime.of(2023, 12, 15, 10, 0));

        LocationDTO location = new LocationDTO(10, 10);
        mockEventInfo.setLocation(location);
        mockEventInfo.setState(StateDTO.OPEN);
        mockEventInfo.setType(EventTypeDTO.PUBLIC);
        mockEventInfo.setOrganizerName("Jane Smith");
        mockEventInfo.setInterests(new HashSet<>(Arrays.asList(InterestDTO.MUSIC)));

        // Simular el comportamiento del servicio
        when(recommendationService.recommendateEventsToUser(userId)).thenReturn(new ArrayList<>(Arrays.asList(mockEventInfo)));

        // Realizar una petición GET al endpoint /api/activate/recommendation/{userId}
        mockMvc.perform(get("/api/activate/recommendation/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Verificar todos los campos del EventInfoDTO
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].maxCapacity").value(100))
                .andExpect(jsonPath("$[0].duration").value(120))
                .andExpect(jsonPath("$[0].name").value("Tech Conference"))
                .andExpect(jsonPath("$[0].description").value("A conference about the latest in tech"))
                .andExpect(jsonPath("$[0].date").value("2023-12-15T10:00:00"))
                .andExpect(jsonPath("$[0].location.latitude").value(10))
                .andExpect(jsonPath("$[0].location.longitude").value(10))
                .andExpect(jsonPath("$[0].state").value("OPEN"))
                .andExpect(jsonPath("$[0].type").value("PUBLIC"))
                .andExpect(jsonPath("$[0].organizerName").value("Jane Smith"))
                .andExpect(jsonPath("$[0].interests[0]").value("MUSIC"));
    }

    @Test
    public void testPairUserWithNoEvents() throws Exception {
        Long userId = 2L;

        // Simular que no hay eventos para el usuario
        when(recommendationService.recommendateEventsToUser(userId)).thenReturn(null);

        // Realizar una petición GET al endpoint /api/activate/recommendation/{userId}
        mockMvc.perform(get("/api/activate/recommendation/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
