package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.OrganizerService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Location;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activate/event/organizer")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
        Location location = new Location(eventRequest.getLatitude(), eventRequest.getLongitude());
        organizerService.createEvent(eventRequest.getMaxCapacity(), eventRequest.getDuration(), eventRequest.getName(), eventRequest.getDescription(), eventRequest.getDate(), location, eventRequest.getType(), eventRequest.getOrganizerId(), eventRequest.getInterests());
        return ResponseEntity.created(null).build();
    }
    @PutMapping("/{organizerId}/cancel/{eventId}")
    public ResponseEntity cancelEvent(@PathVariable Long organizerId, @PathVariable Long eventId) {
        organizerService.cancelEvent(eventId, organizerId);
        return ResponseEntity.ok().build();
    }
}