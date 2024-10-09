package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services.UserService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.EventCommand;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.repository.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.repository.EventQueryRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
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
    public EventCommand mapEventToInfrastructure(Event event) {
        EventCommand eventMapped = eventCommandRepository.findById(event.getId())
                .orElse(new EventCommand());

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

        mapEvaluationToInfrastructure(event.getEvaluations(), eventMapped);

        eventMapped.getEvaluations().forEach(e -> {
            System.out.println("Evaluation: " + e.getId() + " " + e.getComment() + " " + e.getScore() + " " + e.getAuthor());
        });

        return eventMapped;
    }

    private void mapParticipantsToInfrastructure(ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> participants, EventCommand eventCommand) {
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant participant : participants) {
            if (eventCommand.getParticipants().stream().noneMatch(p -> p.getUser().getId().equals(participant.getUser().getId()))) {
                User userParticipant = userRepository.findById(participant.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
                Participant p = new Participant();
                p.setUser(userParticipant);
                p.setEvent(eventCommand);
                eventCommand.getParticipants().add(p);
            }
        }
    }

    private void mapEvaluationToInfrastructure(ArrayList<Evaluation> evaluations, EventCommand eventCommand) {
        for (Evaluation evaluation : evaluations) {
            com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Evaluation e =
                    new com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Evaluation(null, evaluation.getComment(), evaluation.getScore(), evaluation.getAuthor().getUser().getId(), eventCommand);
            eventCommand.getEvaluations().add(e);
        }
    }

    public Event mapEventToDomain(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event event) throws Exception {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userOrganizer = userService.getUser(event.getOrganizer().getId());
        Organizer organizer = new Organizer(userOrganizer);

        HashSet<Interest> interests = new HashSet<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Interest interest : event.getInterests()) {
            interests.add(Interest.valueOf(interest.toString()));
        }
        Event e = new Event(Long.parseLong(event.getId()), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(),
                event.getDate(), new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                State.valueOf(event.getState().toString()),
                EventType.valueOf(event.getType().toString()), organizer, interests);
        e.getParticipants().addAll(mapParticipantsToDomain(event.getParticipants()));
        e.getEvaluations().addAll(mapEvaluationToDomain(event.getEvaluations()));
        return e;
    }

    private ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> mapParticipantsToDomain(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Participant> participants) throws Exception {
        ArrayList<com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant> participantsMapped = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Participant participant : participants) {
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userParticipant = userService.getUser(participant.getUserId());
            participantsMapped.add(new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant(null, userParticipant));
        }
        return participantsMapped;
    }

    private ArrayList<Evaluation> mapEvaluationToDomain(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Evaluation> evaluations) throws Exception {
        ArrayList<Evaluation> evaluationsMapped = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Evaluation evaluation : evaluations) {
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userAuthor = userService.getUser(evaluation.getAuthorId());
            com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant participantAuthor = new com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant(null, userAuthor);
            Evaluation e = new Evaluation(evaluation.getId(), evaluation.getComment(), evaluation.getScore(), participantAuthor);
            evaluationsMapped.add(e);
        }
        return evaluationsMapped;
    }

    public Event mapEventCommandToDomain(EventCommand event) throws Exception {
        com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User userOrganizer = userService.getUser(event.getOrganizer());
        Organizer organizer = new Organizer(userOrganizer);

        HashSet<Interest> interests = new HashSet<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Interest interest : event.getInterests()) {
            interests.add(Interest.valueOf(interest.toString()));
        }

        return new Event(event.getId(), event.getMaxCapacity(), event.getDuration(), event.getName(), event.getDescription(),
                event.getDate(), new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                State.valueOf(event.getState().toString()),
                EventType.valueOf(event.getType().toString()), organizer, interests);
    }
}