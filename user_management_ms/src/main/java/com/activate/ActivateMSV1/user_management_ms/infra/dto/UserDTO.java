package com.activate.ActivateMSV1.user_management_ms.infra.dto;

import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private String email;
    private Set<InterestDTO> interests;
    private LocationDTO location;
}
