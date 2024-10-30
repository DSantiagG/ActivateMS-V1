package com.activate.ActivateMSV1.user_management_ms.infra.repository.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ModLocation {
    private double latitude;
    private double length;
}
