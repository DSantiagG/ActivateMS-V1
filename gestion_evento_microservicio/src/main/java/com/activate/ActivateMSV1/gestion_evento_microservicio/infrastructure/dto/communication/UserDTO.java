package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication;

import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication.InterestDTO;
import com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto.communication.LocationDTO;
import lombok.Data;

import java.util.HashSet;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<InterestDTO> interests;
    private LocationDTO location;
}
