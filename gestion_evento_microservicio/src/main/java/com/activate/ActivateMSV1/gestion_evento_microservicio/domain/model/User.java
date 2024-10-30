package com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model;

import lombok.Getter;

import java.util.HashSet;

@Getter
public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<Interest> interests;
    private Location location;

    public User(Long id, String name, int age, String email, HashSet<Interest> interests, Location location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.interests = interests != null ? interests : new HashSet<>();
        this.location = location != null ? location : new Location(0.0, 0.0);
    }
}