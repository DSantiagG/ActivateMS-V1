package com.activate.ActivateMSV1.gestion_evento_microservicio.infrastructure.repository.event.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventSyncScheduler {

    @Autowired
    private EventSyncService eventSyncService;

    @Scheduled(fixedRate = 5000)
    public void syncEvents() {
        System.out.println("Syncing events...");
        eventSyncService.sync();
    }
}
