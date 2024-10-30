package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.NotificationDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.config.RabbitMQConfig;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.repository.EventRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class EventConsumerService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RecommendationService recommendationService;

    @RabbitListener(queues = RabbitMQConfig.EVENT_QUEUE)
    public void receiveMessage(EventInfoDTO event) {
        Boolean eventAlreadyExists;

        if(event == null) {
            return;
        }

        eventAlreadyExists = eventRepository.findById(event.getId().toString()).orElse(null) != null;
        eventRepository.save(EventMapper.INSTANCE.toRepoModelEvent(event));

        if(eventAlreadyExists) return;

        recommendationService.recommendUsersToEvent(event.getId()).forEach(
                user -> sendNotification(event, user)
        );
    }

    private void sendNotification(EventInfoDTO event, UserDTO user) {
        NotificationDTO notification = createNotification(event, user);
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, notification);
    }
    private NotificationDTO createNotification(EventInfoDTO event, UserDTO user) {
        NotificationDTO notification = new NotificationDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'a las' H ': 'M", new Locale("es", "ES"));
        String formattedDate = event.getDate().format(formatter);

        String title = "¡Nuevo evento recomendado!";
        String description = "¡Hola, " + user.getName() + "! Activate te recomienda el evento '" + event.getName() + "' que se llevará a cabo cerca de ti el " + formattedDate + ". ¡No te lo pierdas <3!";

        notification.setUserId(user.getId());
        notification.setTitle(title);
        notification.setDescription(description);
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        notification.setDate(date);
        return notification;
    }
}
