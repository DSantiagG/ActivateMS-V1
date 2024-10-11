package com.activate.ActivateMSV1.gestion_evento_microservicio.application.service;

import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Event;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.Participant;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.config.RabbitMQConfig;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.EventInfoDTO;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.NotificationDTO;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.mappers.EventAdapter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventPublisherService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    EventAdapter eventAdapter;

    public void publishEvent(Event event) {
        EventInfoDTO eventMappedDTO = eventAdapter.mapEventToEventInfoDTO(event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EVENT_QUEUE, eventMappedDTO);
    }

    public void publishNotifications(Event event, String title, String message) {
        for (Participant participant : event.getParticipants()) {
            NotificationDTO notification = new NotificationDTO(participant.getUser().getId(), title,message, LocalDateTime.now());
            rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, notification);
        }
    }
}
