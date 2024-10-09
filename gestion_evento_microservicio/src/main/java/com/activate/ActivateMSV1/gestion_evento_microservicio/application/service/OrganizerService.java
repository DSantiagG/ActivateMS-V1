package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services.UserAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.EventCommand;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.repository.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class OrganizerService {

    @Autowired
    EventService eventService;
    @Autowired
    EventCommandRepository eventCommandRepository;
    @Autowired
    EventAdapter eventAdapter;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAdapter userAdapter;

    public void createEvent(int maxCapacity, int duration, String name, String description, LocalDateTime date, Location location, EventType type, Long organizerId, HashSet<Interest> interests) throws Exception {
        User userOrganizer = userRepository.findById(organizerId).orElseThrow(() -> new RuntimeException("Organizer not found"));
        Organizer organizer = new Organizer(userAdapter.mapUserToDomain(userOrganizer));
        if(date.isBefore(LocalDateTime.now()))
            throw new RuntimeException("The event date cannot be earlier than the current date");
        Event event = new Event(-1L, maxCapacity, duration, name, description, date, location, type, organizer, interests);
        organizer.createEvent(event);

        EventCommand eventMapped = eventAdapter.mapEventToInfrastructure(event);
        eventCommandRepository.save(eventMapped);
    }

    @Transactional
    public void cancelEvent(Long eventId, Long organizerId) throws Exception {
        User userOrganizer = userRepository.findById(organizerId).orElseThrow(() -> new RuntimeException("Organizer not found"));
        Organizer organizer = new Organizer(userAdapter.mapUserToDomain(userOrganizer));

        ArrayList<Event> organizedEvents = new ArrayList<>();

        for (Event event : eventService.getEventsDomain()) {
            if(event.getOrganizerId().equals(organizerId)) organizedEvents.add(event);
        }
        organizer.setOrganizedEvents(organizedEvents);

        if(organizer.cancelEvent(eventId)) {
            eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(
                    organizer.getOrganizedEvents().stream()
                            .filter(event -> event.getId().equals(eventId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Event not found"))));
        }
    }
}