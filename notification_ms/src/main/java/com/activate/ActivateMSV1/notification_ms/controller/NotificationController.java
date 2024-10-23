package com.activate.ActivateMSV1.notification_ms.controller;

import com.activate.ActivateMSV1.notification_ms.infra.DTO.NotificationDTO;
import com.activate.ActivateMSV1.notification_ms.service.NotificationConsumerService;
import com.activate.ActivateMSV1.notification_ms.infra.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activate/notification")
public class NotificationController {
    @Autowired
    NotificationConsumerService notificationConsumerService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO notification) {
        try {
            notificationConsumerService.consumeNotification(notification);
            return new ResponseEntity<>("Notificación enviada", HttpStatus.OK);
        } catch (DomainException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
