package com.activate.ActivateMSV1.recommendation_ms.infra.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Long userId;
    private String title;
    private String description;
    private Date date;
}
