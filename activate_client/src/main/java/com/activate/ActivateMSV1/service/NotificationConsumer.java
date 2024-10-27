package com.activate.ActivateMSV1.service;

import lombok.Setter;

import javax.swing.*;

public class NotificationConsumer {
    @Setter
    JTextArea txaNotifications;
    Long userId;

    public NotificationConsumer(Long userId) {
        this.userId = userId;
    }

    public void consumeNotification(String notification) {
        txaNotifications.append(notification + "\n");
    }

}
