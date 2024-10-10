package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.config.RabbitMQConfig;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.EventInfoDTO;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.DomainException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.NotFoundException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.UserAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.EventAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.repository.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void createEvent(int maxCapacity, int duration, String name, String description, LocalDateTime date, Location location, EventType type, Long organizerId, HashSet<Interest> interests) {
        User userOrganizer = userRepository.findById(organizerId).orElseThrow(() -> new NotFoundException("Organizer not found"));
        Organizer organizer = new Organizer(userAdapter.mapUserToDomain(userOrganizer));
        if(date.isBefore(LocalDateTime.now()))
            throw new DomainException("The event date cannot be earlier than the current date");
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event = new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event(-1L, maxCapacity, duration, name, description, date, location, type, organizer, interests);
        organizer.createEvent(event);

        Event eventMapped = eventAdapter.mapEventToInfrastructure(event);
        eventCommandRepository.save(eventMapped);

        EventInfoDTO eventMappedDTO = eventAdapter.mapEventToEventInfoDTO(event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EVENT_QUEUE, eventMappedDTO);
    }

    @Transactional
    public void cancelEvent(Long eventId, Long organizerId) {
        User userOrganizer = userRepository.findById(organizerId).orElseThrow(() -> new NotFoundException("Organizer not found"));
        Organizer organizer = new Organizer(userAdapter.mapUserToDomain(userOrganizer));

        ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event> organizedEvents = new ArrayList<>();

        for (com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event : eventService.getEventsDomain()) {
            if(event.getOrganizerId().equals(organizerId)) organizedEvents.add(event);
        }
        organizer.setOrganizedEvents(organizedEvents);

        if(organizer.cancelEvent(eventId)) {
            eventCommandRepository.save(eventAdapter.mapEventToInfrastructure(
                    organizer.getOrganizedEvents().stream()
                            .filter(event -> event.getId().equals(eventId))
                            .findFirst().orElseThrow(() -> new NotFoundException("Event not found"))));
        }
    }
}