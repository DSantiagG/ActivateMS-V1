package com.activate.ActivateMSV1.recommendation_ms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<Interest> interests;
    private Location location;
}
