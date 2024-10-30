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
        };
    }
}