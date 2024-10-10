package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.user_services;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.User;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.config.RabbitMQConfig;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.UserAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAdapter userAdapter;

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void receiveMessage(User user){
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User userMapped = userAdapter.mapUserToInfrastructure(user);
        userRepository.save(userMapped);
    }
}