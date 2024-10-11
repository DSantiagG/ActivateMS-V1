package com.activate.ActivateMSV1.recommendation_ms.domain;

import com.activate.ActivateMSV1.recommendation_ms.infra.exceptions.DomainException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class Recommendation {
    private static final int PROXIMITY_THRESHOLD = 50;
    @Getter
    private HashMap<Long, ArrayList<Event>> pairings;

    public Recommendation() {
        this.pairings = new HashMap<>();
    }

    public void pairUserToEvents(User user, ArrayList<Event> eventsAvailable) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsAvailable) {
            boolean isClose = isClose(user.getLocation(), event.getLocation());
            for (Interest interest : user.getInterests()) {
                if (event.getState() == State.OPEN && event.getInterests().contains(interest) && isClose) {
                    events.add(event);
                    break;
                }
            }
        }
        if(events.isEmpty())
            throw new DomainException("There are no events available for the user");

        if(pairings.containsKey(user.getId()))
            pairings.replace(user.getId(), events);
        else
            pairings.put(user.getId(), events);
    }

    public ArrayList<User> recommendEventToUsers(Event event, ArrayList<User> users) {
        ArrayList<User> usersRecommended = new ArrayList<>();
        if(event.getState() != State.OPEN)
            return usersRecommended;

        for (User user : users) {
            boolean isClose = isClose(user.getLocation(), event.getLocation());
            for (Interest interest : user.getInterests()) {
                if (event.getInterests().contains(interest) && isClose) {
                    usersRecommended.add(user);
                    break;
                }
            }
        }
        return usersRecommended;
    }

    private boolean isClose(Location A, Location B) {
        int distance = (int) Math.sqrt(Math.pow(A.getLatitude() - B.getLatitude(), 2) + Math.pow(A.getLongitude() - B.getLongitude(), 2));
        return distance <= PROXIMITY_THRESHOLD;
    }

    public ArrayList<Event> getRecommendations(Long userId) {
        if(!pairings.containsKey(userId))
            throw new DomainException("There are no recommendations for the user");
        return pairings.get(userId);
    }
}
