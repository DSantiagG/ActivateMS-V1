package com.activate.ActivateMSV1.recommendation_ms.infra.repository;

import com.activate.ActivateMSV1.recommendation_ms.infra.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventRepository extends MongoRepository<Event, String> {
}
