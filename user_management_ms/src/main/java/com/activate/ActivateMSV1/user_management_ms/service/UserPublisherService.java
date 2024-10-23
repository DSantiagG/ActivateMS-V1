package com.activate.ActivateMSV1.user_management_ms.service;

import com.activate.ActivateMSV1.user_management_ms.infra.config.RabbitMQConfig;
import com.activate.ActivateMSV1.user_management_ms.infra.dto.UserDTO;
import com.activate.ActivateMSV1.user_management_ms.infra.mappers.UserAdapter;
import com.activate.ActivateMSV1.user_management_ms.infra.repository.model.ModUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserAdapter userAdapter;

    public void publishUserToEvent(ModUser user) {
        UserDTO userDTO = userAdapter.mapModelUserToDTO(user);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EVENT_QUEUE,userDTO);
    }

    public void publishUserToRecommendation(ModUser user) {
        UserDTO userDTO = userAdapter.mapModelUserToDTO(user);
        rabbitTemplate.convertAndSend(RabbitMQConfig.RECOMMENDATION_QUEUE,userDTO);
    }

}
