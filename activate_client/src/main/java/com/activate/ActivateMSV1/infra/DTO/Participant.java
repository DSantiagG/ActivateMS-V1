package com.activate.ActivateMSV1.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Participant {
    private Long id;
    private Long userId;
    private String name;
}
