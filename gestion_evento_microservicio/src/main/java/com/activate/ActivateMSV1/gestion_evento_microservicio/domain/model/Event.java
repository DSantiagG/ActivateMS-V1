package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class Event {
    @Getter @Setter
    private Long id;
    @Getter
    private int maxCapacity;
    @Getter
    private int duration; // In minutes
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private LocalDateTime date;
    @Getter
    private Location location;
    @Getter @Setter
    private State state;
    @Getter
    private EventType type;
    @Getter
    private Organizer organizer;
    @Getter
    private HashSet<Interest> interests;
    @Getter
    private ArrayList<Participant> participants;
    @Getter
    private ArrayList<Evaluation> evaluations;

    public Event(Long id, int maxCapacity, int duration, String name, String description, LocalDateTime date, Location location, EventType type, Organizer organizer, HashSet<Interest> interests) {
        this(id, maxCapacity, duration, name, description, date, location, State.OPEN, type, organizer, interests);
    }

    public Event(Long id, int maxCapacity, int duration, String name, String description, LocalDateTime date, Location location, State state, EventType type, Organizer organizer, HashSet<Interest> interests) {
        if(duration <= 0)
            throw new DomainException("The duration of the event must be greater than 0");
        if(duration > 1440)
            throw new DomainException("The duration of the event cannot be more than 1 day");
        if(maxCapacity <= 0)
            throw new DomainException("The maximum capacity of the event must be greater than 0");
        if(location == null)
            throw new DomainException("The location of the event cannot be null");

        if(interests == null || interests.isEmpty())
            throw new DomainException("The event must have at least one interest");

        this.id = id;
        this.maxCapacity = maxCapacity;
        this.duration = duration;
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.state = state;
        this.type = type;
        this.organizer = organizer;
        this.interests = interests;
        this.participants = new ArrayList<>();
        this.evaluations = new ArrayList<>();
    }

    public void cancel() {
        if(this.state.equals(State.FINISHED))
            throw new DomainException("Cannot cancel an event that has already finished");
        if(this.state.equals(State.CANCELED))
            throw new DomainException("Cannot cancel an event that has already been canceled");
        this.state = State.CANCELED;
    }
    public void close() {
        if(!this.state.equals(State.OPEN))
            throw new DomainException("Cannot close an event that is not open");
        this.state = State.CLOSED;
    }
    public void reopen() {
        if(!this.state.equals(State.CLOSED))
            throw new DomainException("Cannot reopen an event that is not closed");
        this.state = State.OPEN;
    }
    public void finish() {
        if(!this.state.equals(State.IN_PROGRESS))
            throw new DomainException("Cannot finish an event that is not in progress");
        this.state = State.FINISHED;
    }

    public void start() {
        if(!(this.state.equals(State.OPEN) || this.state.equals(State.CLOSED)))
            throw new DomainException("Cannot start an event that is not open or closed");
        this.state = State.IN_PROGRESS;
    }

    public void changeType(){
        if(this.type.equals(EventType.PUBLIC))
            this.type = EventType.PRIVATE;
        else
            this.type = EventType.PUBLIC;
    }

    public boolean updateMaxCapacity(int maxCapacity){
        if(maxCapacity <= 0)
            throw new DomainException("The maximum capacity of the event must be greater than 0");
        if(this.state.equals(State.FINISHED))
            throw new DomainException("Cannot update the capacity of an event that has already finished");
        if(this.state.equals(State.CANCELED))
            throw new DomainException("Cannot update the capacity of an event that has been canceled");
        if(this.participants.size() > maxCapacity)
            throw new DomainException("Cannot update the capacity of an event that has already exceeded the new capacity");
        this.maxCapacity = maxCapacity;
        return true;
    }

    public void updateDate(LocalDateTime date){
        if(date.isBefore(LocalDateTime.now()))
            throw new DomainException("The date of the event cannot be earlier than the current date");
        if(this.state.equals(State.FINISHED))
            throw new DomainException("Cannot update the date of an event that has already finished");
        if(this.state.equals(State.CANCELED))
            throw new DomainException("Cannot update the date of an event that has been canceled");
        if(this.state.equals(State.IN_PROGRESS))
            throw new DomainException("Cannot update the date of an event that has already started");
        if(date.isBefore(LocalDateTime.now().plusHours(12)))
            throw new DomainException("Cannot update the date of the event to less than twelve hours before its start");
        this.date = date;
    }

    public void addEvaluation(Evaluation evaluation){
        if(!this.state.equals(State.FINISHED))
            throw new DomainException("Cannot add an evaluation to an event that has not finished");
        if (this.participants.stream().noneMatch(p -> p.getUser().getId().equals(evaluation.getAuthor().getUser().getId())))
            throw new DomainException("The evaluator is not a participant of the event");

        this.evaluations.add(evaluation);
    }

    public boolean addParticipant(Participant participant){
        if(!this.state.equals(State.OPEN))
            throw new DomainException("Cannot add a participant to an event that is not open");
        if(this.participants.contains(participant))
            throw new DomainException("The participant is already registered for the event");
        if(participants.stream().anyMatch(p -> p.getUser().getId().equals(participant.getUser().getId())))
            throw new DomainException("The participant is already registered for the event");
        this.participants.add(participant);
        if(this.participants.size() == this.maxCapacity)
            close();
        return true;
    }

    public boolean removeParticipant(Long participantId){
        if(!(this.state.equals(State.OPEN) || this.state.equals(State.CLOSED)))
            throw new DomainException("Cannot remove a participant from an event that is not open or closed");
        if (participants.stream().noneMatch(p -> p.getUser().getId().equals(participantId))) {
            throw new DomainException("The participant is not registered for the event");
        }
        participants.removeIf(p -> p.getUser().getId().equals(participantId));
        if(this.state.equals(State.CLOSED) && this.participants.size() < this.maxCapacity)
            this.reopen();
        return true;
    }

    public String getOrganizerName() {
        return organizer.getName();
    }
    public Long getOrganizerId() {return organizer.getId();}
}