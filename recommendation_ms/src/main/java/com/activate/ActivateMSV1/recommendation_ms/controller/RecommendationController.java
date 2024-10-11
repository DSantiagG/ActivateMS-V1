package com.activate.ActivateMSV1.recommendation_ms.controller;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/pair/{userId}")
    public ResponseEntity<?> pair(@PathVariable Long userId) {
        ArrayList<EventInfoDTO> pairedEvents;
        pairedEvents = recommendationService.pair(userId);

        if(pairedEvents == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pairedEvents);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<EventInfoDTO>> getRecommendations(@PathVariable Long userId) {
        ArrayList<EventInfoDTO> recommendations;
        recommendations = recommendationService.getRecommendations(userId);

        if(recommendations == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recommendations);
    }

}
