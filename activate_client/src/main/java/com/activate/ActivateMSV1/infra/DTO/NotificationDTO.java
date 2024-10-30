package com.activate.ActivateMSV1.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long userId;
    private String title;
    private String description;
    private Date date;
}
