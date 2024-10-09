package com.activate.ActivateMSV1.recommendation_ms.infra.repository;

import com.activate.ActivateMSV1.recommendation_ms.infra.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, String> {
}
