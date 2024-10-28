package com.activate.ActivateMSV1.notification_ms.service;

import com.activate.ActivateMSV1.notification_ms.infra.DTO.NotificationDTO;
import com.activate.ActivateMSV1.notification_ms.infra.config.RabbitMQConfig;
import com.activate.ActivateMSV1.notification_ms.infra.exceptions.DomainException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DynamicQueueService dynamicQueueService;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void consumeNotification(NotificationDTO notification) {
        try {
            System.out.println("Mensaje recibido de NOTIFICATION_QUEUE: " + notification);

            if (notification.getUserId() == null || notification.getTitle() == null) {
                throw new DomainException("UserId o Title no pueden ser nulos");
            }

            dynamicQueueService.createQueueForClient(notification.getUserId());
            dynamicQueueService.sendMessageToClient(notification.getUserId(), notification);

            System.out.println("Notificación enviada a userNotiQueue: " + notification);
        } catch (Exception e) {

            throw new DomainException("Error al consumir la notificación: " + e.getMessage());
        }
    }
}
