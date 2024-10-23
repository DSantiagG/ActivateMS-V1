package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.exceptions.NotFoundException;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.UserAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAdapter userAdapter;

    /**
     * Get user by id
     * @param id user id
     * @return user
     */
    public User getUser(Long id) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return userAdapter.mapUserToDomain(user);
    }
}