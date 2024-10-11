package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.UserService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.NotFoundException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.EventQueryRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class EventAdapter {

    @Autowired
    EventCommandRepository eventCommandRepository;
    @Autowired
    EventQueryRepository eventQueryRepository;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public Event mapEventToInfrastructure(com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event) {
        Event eventMapped = eventCommandRepository.findById(event.getId())
                .orElse(new Event());

        eventMapped.setMaxCapacity(event.getMaxCapacity());
        eventMapped.setDuration(event.getDuration());
        eventMapped.setName(event.getName());
        eventMapped.setDescription(event.getDescription());
        eventMapped.setDate(event.getDate());
        eventMapped.setLocation(new com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()));
        eventMapped.setType(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.EventType.valueOf(event.getType().toString()));
        eventMapped.setState(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.State.valueOf(event.getState().toString()));
        eventMapped.setOrganizer(event.getOrganizerId());

        eventMapped.getInterests().clear();
        for (Interest interest : event.getInterests()) {
            eventMapped.getInterests().add(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Interest.valueOf(interest.toString()));
        }

        eventMapped.getParticipants().removeIf(p -> event.getParticipants().stream()
                .noneMatch(ep -> ep.getUser().getId().equals(p.getUser().getId())));
        mapParticipantsToInfrastructure(event.getParticipants(), eventMapped);

        eventMapped.getEvaluations().removeIf(e -> event.getEvaluations().stream()
                .noneMatch(ev -> ev.getId().equals(e.getId())));
        mapEvaluationToInfrastructure(event.getEvaluations(), eventMapped);

        eventMapped.getEvaluations().forEach(e -> {
            System.out.println("Evaluation: " + e.getId() + " " + e.getComment() + " " + e.getScore() + " " + e.getAuthor());
        });

        return eventMapped;
    }

    private void mapParticipantsToInfrastructure(ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> participants, Event event) {
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant participant : participants) {
            if (event.getParticipants().stream().noneMatch(p -> p.getUser().getId().equals(participant.getUser().getId()))) {
                User userParticipant = userRepository.findById(participant.getUser().getId()).orElseThrow(() -> new NotFoundException("User not found"));
                Participant p = new Participant();
                p.setUser(userParticipant);
                p.setEvent(event);
                event.getParticipants().add(p);
            }
        }
    }

    private void mapEvaluationToInfrastructure(ArrayList<Evaluation> evaluations, Event event) {
        for (Evaluation evaluation : evaluations) {
            if(event.getEvaluations().stream().noneMatch(e -> e.getId().equals(evaluation.getId()))) {
                com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Evaluation e =
                        new com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Evaluation(null, evaluation.getComment(), evaluation.getScore(), evaluation.getAuthor().getUser().getId(), event);
                event.getEvaluations().add(e);
            }
        }
    }

    public com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event mapEventToDomain(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userOrganizer = userService.getUser(event.getOrganizer().getId());
        Organizer organizer = new Organizer(userOrganizer);

        HashSet<Interest> interests = new HashSet<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Interest interest : event.getInterests()) {
            interests.add(Interest.valueOf(interest.toString()));
        }
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event e = new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event(Long.parseLong(event.getId()), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(),
                event.getDate(), new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                State.valueOf(event.getState().toString()),
                EventType.valueOf(event.getType().toString()), organizer, interests);
        e.getParticipants().addAll(mapParticipantsToDomain(event.getParticipants()));
        e.getEvaluations().addAll(mapEvaluationToDomain(event.getEvaluations()));
        return e;
    }

    private ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> mapParticipantsToDomain(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Participant> participants) {
        ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> participantsMapped = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Participant participant : participants) {
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userParticipant = userService.getUser(participant.getUserId());
            participantsMapped.add(new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant(null, userParticipant));
        }
        return participantsMapped;
    }

    private ArrayList<Evaluation> mapEvaluationToDomain(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Evaluation> evaluations) {
        ArrayList<Evaluation> evaluationsMapped = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Evaluation evaluation : evaluations) {
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userAuthor = userService.getUser(evaluation.getAuthorId());
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant participantAuthor = new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant(null, userAuthor);
            Evaluation e = new Evaluation(evaluation.getId(), evaluation.getComment(), evaluation.getScore(), participantAuthor);
            evaluationsMapped.add(e);
        }
        return evaluationsMapped;
    }

    public com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event mapEventCommandToDomain(Event event) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userOrganizer = userService.getUser(event.getOrganizer());
        Organizer organizer = new Organizer(userOrganizer);

        HashSet<Interest> interests = new HashSet<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Interest interest : event.getInterests()) {
            interests.add(Interest.valueOf(interest.toString()));
        }

        return new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(),
                event.getDate(), new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                State.valueOf(event.getState().toString()),
                EventType.valueOf(event.getType().toString()), organizer, interests);
    }

    public EventInfo mapEventToEventInfo(com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event event){
        return new EventInfo(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(), event.getDate(), event.getLocation(), event.getState(), event.getType(), event.getOrganizerName(), event.getInterests());
    }

    public EventInfoDTO mapEventToEventInfoDTO(com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event domainEvent){
        EventInfo event = mapEventToEventInfo(domainEvent);

        HashSet<InterestDTO> interests = new HashSet<>();
        for (Interest interest : event.getInterests()) {
            interests.add(InterestDTO.valueOf(interest.name()));
        }
        return new EventInfoDTO(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(), event.getDate(), new LocationDTO(event.getLocation().getLatitude(), event.getLocation().getLongitude()), StateDTO.valueOf(event.getState().name()), EventTypeDTO.valueOf(event.getType().name()), event.getOrganizerName(), interests);
    }
}