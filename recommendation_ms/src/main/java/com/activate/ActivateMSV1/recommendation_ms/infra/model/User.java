package com.activate.ActivateMSV1.recommendation_ms.infra.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

@Document("users")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private int age;
    private String email;
    private HashSet<Interest> interests;
    private Location location;
}
