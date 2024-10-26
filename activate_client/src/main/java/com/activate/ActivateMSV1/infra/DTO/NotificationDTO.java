package com.activate.ActivateMSV1.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class NotificationDTO {
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime date;
}
