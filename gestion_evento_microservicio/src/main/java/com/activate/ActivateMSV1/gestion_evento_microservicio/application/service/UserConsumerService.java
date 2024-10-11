package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.config.RabbitMQConfig;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication.UserDTO;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.UserAdapter;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAdapter userAdapter;

    /**
     * Receive a message from the queue and save the user in the database
     * @param user user data
     */
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void receiveMessage(UserDTO user){
        com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model.User userMapped = userAdapter.mapUserDTOToInfrastructure(user);
        userRepository.save(userMapped);
    }
}