package com.activate.ActivateMSV1.recommendation_ms.infra.DTO;

import com.activate.ActivateMSV1.recommendation_ms.domain.Interest;
import com.activate.ActivateMSV1.recommendation_ms.domain.Location;
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
