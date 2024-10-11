package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.config.RabbitMQConfig;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.UserMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void receiveMessage(UserDTO user) {
        if(user == null) {
            return;
        }

        userRepository.save(UserMapper.INSTANCE.toRepoModelUser(user));
    }
}
