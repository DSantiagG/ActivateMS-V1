package com.activate.ActivateMSV1.notification_ms.service;
import com.activate.ActivateMSV1.notification_ms.infra.DTO.NotificationDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicQueueService {
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange dynamicExchange;

    public void createQueueForClient(Long clientId) {
        // Nombre de la cola específico para el cliente
        String queueName = "userNotificationsQueue_" + clientId;

        // Crear la cola y enlazarla al exchange con una routing key basada en el ID del cliente
        Queue clientQueue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(clientQueue);

        Binding binding = BindingBuilder.bind(clientQueue).to(dynamicExchange).with(Long.toString(clientId));
        rabbitAdmin.declareBinding(binding);
    }

    public void sendMessageToClient(Long clientId, NotificationDTO message) {
        // Enviar mensaje al exchange con la clave de enrutamiento del cliente
        rabbitTemplate.convertAndSend(dynamicExchange.getName(), Long.toString(clientId), message);
    }
}
