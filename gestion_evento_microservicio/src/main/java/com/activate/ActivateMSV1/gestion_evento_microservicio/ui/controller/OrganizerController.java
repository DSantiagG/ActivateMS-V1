package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.OrganizerService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Location;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/organizer")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
        Location location = new Location(eventRequest.getLatitude(), eventRequest.getLongitude());
        try {
            organizerService.createEvent(eventRequest.getMaxCapacity(), eventRequest.getDuration(), eventRequest.getName(), eventRequest.getDescription(), eventRequest.getDate(), location, eventRequest.getType(), eventRequest.getOrganizerId(), eventRequest.getInterests());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.created(null).build();
    }
    @PutMapping("/{organizerId}/{eventId}/cancel")
    public ResponseEntity cancelEvent(@PathVariable Long organizerId, @PathVariable Long eventId) {
        try{
            organizerService.cancelEvent(eventId, organizerId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}