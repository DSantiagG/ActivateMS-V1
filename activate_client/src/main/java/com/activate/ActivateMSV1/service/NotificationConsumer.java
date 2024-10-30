package com.activate.ActivateMSV1.service;

import com.activate.ActivateMSV1.infra.DTO.NotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.rabbitmq.client.*;
import lombok.Setter;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationConsumer {
    @Setter
    JTextArea txaNotifications;
    Long userId;

    private static final String EXCHANGE_NAME = "notificationExchange";
    private static final String HOST = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    private Connection connection;
    private Channel channel;

    public NotificationConsumer(Long userId) {
        this.userId = userId;
    }

    public void connectWithServer() throws IOException, TimeoutException {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            String queueName = "userNotificationsQueue_" + userId;

            channel.queueDeclare(queueName, true, false, false, null);

            // Verificar si el intercambio ya existe
            try {
                channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            } catch (Exception e) {
                System.out.println("Intercambio ya existe o error al declararlo: " + e.getMessage());
            }

            channel.queueBind(queueName, EXCHANGE_NAME, Long.toString(userId));

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                try {
                    NotificationDTO notification = mapper.readValue(message, NotificationDTO.class);
                    System.out.println("Mensaje recibido: " + notification);
                    showNotification(notification);
                } catch (Exception e) {
                    System.out.println("Error al deserializar el mensaje: " + e.getMessage());
                }
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    private void showNotification(NotificationDTO notification) {
        String notificationMessage = "Título: " + notification.getTitle() + "\n" +
                "Descripción: " + notification.getDescription() + "\n" +
                "Fecha: " + notification.getDate() + "\n";
        txaNotifications.append(notificationMessage + "\n");
    }
}