package com.activate.ActivateMSV1.recommendation_ms.domain;

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

    public void pairUser(User user, ArrayList<Event> eventsAvailable) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsAvailable) {
            boolean isClose = isClose(user.getLocation(), event.getLocation());
            for (Interest interest : user.getInterests()) {
                if (event.getInterests().contains(interest) && isClose) {
                    events.add(event);
                    break;
                }
            }
        }
        if(events.isEmpty())
            throw new RuntimeException("There are no events available for the user");

        if(pairings.containsKey(user.getId()))
            pairings.replace(user.getId(), events);
        else
            pairings.put(user.getId(), events);
    }

    public void recommendEvent(Event event, ArrayList<User> users) {
        for (User user : users) {
            boolean isClose = isClose(user.getLocation(), event.getLocation());
            for (Interest interest : user.getInterests()) {
                if(pairings.containsKey(user.getId()) && pairings.get(user.getId()).stream().anyMatch(e -> e.getId().equals(event.getId()))) break;
                if (event.getInterests().contains(interest) && isClose) {
                    if(pairings.containsKey(user.getId())) {
                        ArrayList<Event> events = pairings.get(user.getId());
                        events.add(event);
                        pairings.replace(user.getId(), events);
                    } else {
                        ArrayList<Event> events = new ArrayList<>();
                        events.add(event);
                        pairings.put(user.getId(), events);
                    }
                }
            }
        }
    }

    private boolean isClose(Location A, Location B) {
        int distancia = (int) Math.sqrt(Math.pow(A.getLatitude() - B.getLatitude(), 2) + Math.pow(A.getLongitude() - B.getLongitude(), 2));
        return distancia <= PROXIMITY_THRESHOLD;
    }

    public ArrayList<Event> getRecommendations(Long userId) {
        if(!pairings.containsKey(userId))
            throw new RuntimeException("There are no recommendations for the user");
        return pairings.get(userId);
    }
}
