package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.activate.ActivateMSV1.recommendation_ms.infra.model.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(Long id) {
        User user = userRepository.findById(id.toString()).orElse(null);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
