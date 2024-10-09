package com.activate.ActivateMSV1.recommendation_ms.domain;

import lombok.Data;

import java.util.HashSet;

@Data
public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<Interest> interests;
    private Location location;
}
