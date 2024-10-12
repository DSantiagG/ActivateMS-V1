package com.activate.ActivateMSV1.user_management_ms.infra.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class ModUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = ModInterest.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interest")
    @Fetch(FetchMode.JOIN)
    private Set<ModInterest> modInterests;
    @Embedded
    private ModLocation modLocation;

}