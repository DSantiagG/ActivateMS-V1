package com.activate.ActivateMSV1.recommendation_ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> pair(Long userId) {
        ArrayList<EventInfoDTO> pairedEvents;
        try{
            pairedEvents = recommendationService.pair(userId);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }

        if(pairedEvents == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pairedEvents);
    }

    //TODO: Esta se debe llamar leyendo de la cola de recomendacion de eventos
    /*
    public void recommendEvent(Long eventId) {
        recommendationService.recommendEvent(eventId);
    }*/

    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<EventInfoDTO>> getRecommendations(Long userId) {
        ArrayList<EventInfoDTO> recommendations;
        try{
            recommendations = recommendationService.getRecommendations(userId);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        if(recommendations == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recommendations);
    }

}
