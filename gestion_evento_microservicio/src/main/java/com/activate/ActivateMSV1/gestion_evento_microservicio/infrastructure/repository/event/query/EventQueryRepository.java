package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.query.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventQueryRepository extends MongoRepository<Event, String> {
}
