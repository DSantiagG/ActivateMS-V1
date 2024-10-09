package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Interest;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Location;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAdapter {
    @Autowired
    UserRepository userRepository;

    public com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User mapUserToInfrastructure(User user) {
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User userMapped = userRepository.findById(user.getId())
                .orElse(new com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User());

        userMapped.setName(user.getName());
        userMapped.setAge(user.getAge());
        userMapped.setEmail(user.getEmail());
        Set<com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.Interest> interestsMapped = new HashSet<>();
        for (Interest interest : user.getInterests()) {
            interestsMapped.add(com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.Interest.valueOf(interest.toString()));
        }
        userMapped.setInterests(interestsMapped);
        userMapped.setLocation(new com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.Location(user.getLocation().getLatitude(), user.getLocation().getLongitude()));
        return userMapped;
    }

    public User mapUserToDomain (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User user) throws Exception {
        HashSet<Interest> interestsMapped = new HashSet<>();
        for (com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.Interest interestMapped : user.getInterests()) {
            interestsMapped.add(Interest.valueOf(interestMapped.toString()));
        }
        return new User(user.getId(), user.getName(), user.getAge(), user.getEmail(), interestsMapped, new Location(user.getLocation().getLatitude(), user.getLocation().getLongitude()));
    }
}