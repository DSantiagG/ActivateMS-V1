package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBCleaner implements DisposableBean {

    private final MongoTemplate mongoTemplate;

    public MongoDBCleaner(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void destroy() throws Exception {
        mongoTemplate.getDb().drop();
    }
}