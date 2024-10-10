package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Long userId;
    private String title;
    private String description;
    private Date date;
}
