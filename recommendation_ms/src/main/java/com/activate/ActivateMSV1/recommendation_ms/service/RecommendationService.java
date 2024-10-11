package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.domain.Event;
import com.activate.ActivateMSV1.recommendation_ms.domain.Recommendation;
import com.activate.ActivateMSV1.recommendation_ms.domain.User;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.exceptions.ServiceValidationException;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecommendationService {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    Recommendation recommendationDomain = new Recommendation();

    public ArrayList<EventInfoDTO> recommendateEventsToUser(Long userId) {
        ArrayList<Event> domainEvents = new ArrayList<>();
        ArrayList<EventInfoDTO> eventsDTO = new ArrayList<>();

        User user = UserMapper.INSTANCE.toDomainUser(userService.getUser(userId));

        if(user == null) {
            throw new ServiceValidationException("User not found");
        }

        eventService.getEvents()
                .forEach(
                        event -> domainEvents.add(EventMapper.INSTANCE.toDomainEvent(event))
                );

        recommendationDomain.recommendateEventsToUser(user, domainEvents)
                .forEach(
                    event -> eventsDTO.add(EventMapper.INSTANCE.toEventInfoDTO(event))
                );

        return eventsDTO;
    }

    public ArrayList<UserDTO> recommendUsersToEvent(Long eventId){
        Event event = EventMapper.INSTANCE.toDomainEvent(eventService.getEvent(eventId));
        ArrayList<User> users = new ArrayList<>();
        ArrayList<UserDTO> usersRecommended = new ArrayList<>();

        userService.getAllUsers()
                .forEach(
                        user -> users.add(UserMapper.INSTANCE.toDomainUser(user))
                );

        recommendationDomain.recommendUsersToEvent(event, users)
                .forEach(
                        user -> usersRecommended.add(UserMapper.INSTANCE.toUserDTO(user))
                );

        return usersRecommended;
    }
}
