package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
