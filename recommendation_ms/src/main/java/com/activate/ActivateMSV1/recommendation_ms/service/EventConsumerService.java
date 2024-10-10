package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.config.RabbitMQConfig;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.UserMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.EventRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {
    @Autowired
    private EventRepository eventRepository;

    @RabbitListener(queues = RabbitMQConfig.EVENT_QUEUE)
    public void receiveMessage(EventInfoDTO event) {
        Boolean eventAlreadyExists;

        if(event == null) {
            return;
        }

        eventAlreadyExists = eventRepository.findById(event.getId().toString()).orElse(null) != null;
        eventRepository.save(EventMapper.INSTANCE.toRepoModelEvent(event));

        if(eventAlreadyExists) return;

        //TODO: Emparejarlo con los usuarios y enviarlo a notificaciones


    }
}
