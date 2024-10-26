package com.activate.ActivateMSV1;

import com.activate.ActivateMSV1.infra.DTO.*;
import com.activate.ActivateMSV1.presentation.LoginView;
import com.activate.ActivateMSV1.service.EventService;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LoginView loginView = new LoginView();
        loginView.show();

        // Crear un objeto Event
        LocationDTO location = new LocationDTO(40.7128, -74.0060);
        StateDTO state = StateDTO.OPEN;
        EventTypeDTO type = EventTypeDTO.PUBLIC;
        HashSet<InterestDTO> interests = new HashSet<>();

        EventInfoDTO event = new EventInfoDTO(
                1L,
                100,
                120,
                "Tech Conference",
                "A conference about the latest in technology.",
                LocalDateTime.now(),
                location,
                state,
                type,
                "John Doe",
                interests
        );

        try{
            boolean response = EventService.postEvent(event, 1L);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Consultar evento
        try{
            EventDTO eventResponse = EventService.getEvento(89L);
            System.out.println("Response: " + eventResponse.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}