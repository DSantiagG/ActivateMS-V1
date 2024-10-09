package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAdapter userAdapter;

    public User getUser(Long id) throws Exception {
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        return userAdapter.mapUserToDomain(user);
    }

    public ArrayList<User> getUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User user : userRepository.findAll()) {
            users.add(userAdapter.mapUserToDomain(user));
        }
        return users;
    }
}