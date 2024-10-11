package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventCommandRepository extends CrudRepository<Event, Long> {

    List<Event> findAllByLastModifiedDateAfter(LocalDateTime lastModifiedDate);

    @Modifying
    @Query("DELETE FROM Participant p WHERE p.event.id = :eventId AND p.user.id = :userId")
    void deleteParticipantFromEvent(@Param("eventId") Long eventId, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Event e SET e.lastModifiedDate = :now WHERE e.id = :eventId")
    void updateLastModifiedDate(@Param("eventId") Long eventId, @Param("now") LocalDateTime now);
}