package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.sync;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.EventCommandRepository;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.*;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventSyncService {

    LocalDateTime lastSyncDate = LocalDateTime.now();

    @Autowired
    EventCommandRepository eventCommandRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Transactional
    public void sync() {
        updateEvent();
        lastSyncDate = LocalDateTime.now();
    }

    private void updateEvent() {
        List<Event> modifiedEvents = getEventsToSync();
        for(Event event: modifiedEvents) {
            Query query = new Query(Criteria.where("id").is(event.getId().toString()));
            Update update = new Update();

            update.set("maxCapacity", event.getMaxCapacity());
            update.set("duration", event.getDuration());
            update.set("name", event.getName());
            update.set("description", event.getDescription());
            update.set("date", event.getDate());
            update.set("location", event.getLocation());
            update.set("state", event.getState());
            update.set("type", event.getType());
            update.set("organizer", getOrganizer(event.getOrganizer()));
            update.set("interests", event.getInterests());
            update.set("participants", getParticipants(event.getParticipants()));
            update.set("evaluations", getEvaluations(event.getEvaluations()));

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event.class);
            System.out.println("Event " + event.getId().toString() + " updated");
        }
    }

    private List<Event> getEventsToSync() {
        return eventCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);
    }

    private Organizer getOrganizer(Long id) {
        String name = userRepository.findById(id).get().getName();
        return new Organizer(id, name);
    }

    private List<Participant> getParticipants(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Participant> participantsCommand){
        return participantsCommand.stream().map(participantCommand -> new Participant(participantCommand.getId(), participantCommand.getUser().getId(), participantCommand.getUser().getName())).collect(Collectors.toList());
    }

    private List<Evaluation> getEvaluations(List<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Evaluation> evaluationsCommand){
        return evaluationsCommand.stream()
                .map(evaluationCommand -> {
                    User user = userRepository.findById(evaluationCommand.getAuthor()).orElseThrow(() -> new RuntimeException("User not found"));
                    return new Evaluation(
                            evaluationCommand.getId(),
                            evaluationCommand.getComment(),
                            evaluationCommand.getScore(),
                            user.getName(),
                            user.getId()
                    );
                })
                .collect(Collectors.toList());
    }
}