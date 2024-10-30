package com.activate.ActivateMSV1.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {
    private String id;
    private int maxCapacity;
    private int duration; // In minutes
    private String name;
    private String description;
    private LocalDateTime date;
    private LocationDTO location;
    private StateDTO state;
    private EventTypeDTO type;
    private OrganizerDTO organizer;
    private Set<InterestDTO> interests;
    private List<Participant> participants;
    private List<EvaluationDTO> evaluations;
}
