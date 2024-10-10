package com.activate.ActivateMSV1.recommendation_ms.infra.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    public static final String USER_QUEUE = "userQueue";
    public static final String EVENT_QUEUE = "eventQueueRecommendation";

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(EVENT_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}