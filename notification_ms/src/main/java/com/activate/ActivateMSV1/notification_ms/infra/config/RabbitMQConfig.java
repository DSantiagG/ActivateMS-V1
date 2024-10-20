package com.activate.ActivateMSV1.notification_ms.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    public static final String NOTIFICATION_QUEUE = "notificationQueue";
    public static final String USER_NOTI_QUEUE = "userNotiQueue";
    public static final String EXCHANGE_NAME = "notificationExchange"; // Nombre del Exchange
    public static final String ROUTING_KEY_USER_NOTI = "userNotiKey"; // Clave de enrutamiento para notificaciones de usuario

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue userNotiQueue() {
        return new Queue(USER_NOTI_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingUserNotiQueue(DirectExchange directExchange, Queue userNotiQueue) {
        return BindingBuilder.bind(userNotiQueue).to(directExchange).with(ROUTING_KEY_USER_NOTI);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
