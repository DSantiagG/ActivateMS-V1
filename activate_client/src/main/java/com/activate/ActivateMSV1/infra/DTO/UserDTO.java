package com.activate.ActivateMSV1.infra.DTO;

import com.activate.ActivateMSV1.infra.DTO.InterestDTO;
import com.activate.ActivateMSV1.infra.DTO  .LocationDTO;
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
