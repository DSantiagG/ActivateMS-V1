package com.activate.ActivateMSV1.recommendation_ms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/activate/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> recommendateEventsToUser(@PathVariable Long userId) {
        ArrayList<EventInfoDTO> pairedEvents;
        pairedEvents = recommendationService.recommendateEventsToUser(userId);

        if(pairedEvents == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pairedEvents);
    }

}
