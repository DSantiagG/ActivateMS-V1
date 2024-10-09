package com.activate.ActivateMSV1.gestion_evento_microservicio.ui.controller;

import com.activate.ActivateMSV1.gestion_evento_microservicio.application.service.ParticipantService;
import com.activate.ActivateMSV1.gestion_evento_microservicio.domain.model.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    public ArrayList<EventInfo> getParticipantEvents(Long participantId) throws Exception {
        return participantService.getParticipantEvents(participantId);
    }
}