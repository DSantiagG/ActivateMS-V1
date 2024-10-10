package com.activate.ActivateMSV1.recommendation_ms.infra.config;

import com.activate.ActivateMSV1.recommendation_ms.infra.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class MongoDBInitializer {

    @Bean
    CommandLineRunner init(MongoTemplate mongoTemplate) {
        return args -> {
            // Inicializar datos aquí
            // Ejemplo: mongoTemplate.save(new Usuario(...));
            User user = new User();
            user.setId("1");
            user.setName("John Doe");
            user.setEmail("jhon@gmail.com");
            user.setAge(25);
            user.setLocation(new Location(234,234));
            user.setInterests(new HashSet<>(Set.of(Interest.LITERATURE, Interest.ART)));
            mongoTemplate.save(user);

            Event event = new Event();
            event.setId("1");
            event.setName("Event 1");
            event.setLocation(new Location(234,234));
            event.setInterests(new HashSet<>(Set.of(Interest.LITERATURE, Interest.ART)));
            event.setMaxCapacity(100);
            event.setDate(LocalDateTime.now());
            event.setOrganizerName("John pepe");
            event.setDescription("Description");
            event.setType(EventType.PUBLIC);
            event.setState(State.OPEN);
            mongoTemplate.save(event);


        };
    }
}