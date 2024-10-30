package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.user.model;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.command.model.Participant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user_data")
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Interest.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interest")
    @Fetch(FetchMode.JOIN)
    private Set<Interest> interests;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Participant> participants;

}