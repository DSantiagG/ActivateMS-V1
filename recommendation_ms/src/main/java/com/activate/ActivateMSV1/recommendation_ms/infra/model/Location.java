package com.activate.ActivateMSV1.recommendation_ms.infra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
}
