package com.activate.ActivateMSV1.recommendation_ms.service;

import com.activate.ActivateMSV1.recommendation_ms.domain.Event;
import com.activate.ActivateMSV1.recommendation_ms.domain.Recommendation;
import com.activate.ActivateMSV1.recommendation_ms.domain.User;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.EventMapper;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import com.activate.ActivateMSV1.recommendation_ms.infra.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecommendationService {

    Recommendation recommendationDomain = new Recommendation();
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    public ArrayList<EventInfoDTO> pair(Long userId) {
        ArrayList<Event> domainEvents = new ArrayList<>();
        ArrayList<EventInfoDTO> eventsDTO = new ArrayList<>();
        User user = UserMapper.INSTANCE.toDomainUser(userService.getUser(userId));

        eventService.getEvents()
                .forEach(
                        event -> domainEvents.add(EventMapper.INSTANCE.toDomainEvent(event))
                );

        recommendationDomain.pairUser(user, domainEvents);
        recommendationDomain.getRecommendations(userId).forEach(
                event -> eventsDTO.add(EventMapper.INSTANCE.toEventInfoDTO(event))
        );

        return eventsDTO;
    }

    public void recommendEvent(Long eventId){
        Event event = EventMapper.INSTANCE.toDomainEvent(eventService.getEvent(eventId));
        ArrayList<User> users = new ArrayList<>();

        userService.getAllUsers()
                .forEach(
                        user -> users.add(UserMapper.INSTANCE.toDomainUser(user))
                );

        recommendationDomain.recommendEvent(event, users);
    }

    public ArrayList<EventInfoDTO> getRecommendations(Long userId) {
        ArrayList<EventInfoDTO> eventsRecommendations = new ArrayList<>();

        recommendationDomain.getRecommendations(userId).forEach(
                event -> eventsRecommendations.add(EventMapper.INSTANCE.toEventInfoDTO(event))
        );

        return eventsRecommendations;
    }

}
