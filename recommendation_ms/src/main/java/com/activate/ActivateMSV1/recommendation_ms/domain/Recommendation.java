package com.activate.ActivateMSV1.recommendation_ms.domain;

import com.activate.ActivateMSV1.recommendation_ms.infra.exceptions.DomainException;

import java.util.ArrayList;

public class Recommendation {
    private static final int PROXIMITY_THRESHOLD = 50;

    public Recommendation() {

    }

    /**
     * Recommend events to a user based on their interests and location
     * @param user User to recommend events
     * @param eventsAvailable List of events available
     * @return List of events recommended to the user
     * */
    public ArrayList<Event> recommendateEventsToUser(User user, ArrayList<Event> eventsAvailable) {
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

        return events;
    }

    /**
     * Recommend users to an event based on their interests and location
     * @param event Event to recommend users
     * @param users List of users available
     * @return List of users recommended to the event
     * */
    public ArrayList<User> recommendUsersToEvent(Event event, ArrayList<User> users) {
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

    /**
     * Check if two locations are close
     * @param A location A
     * @param B location B
     * @return true if the distance between A and B is less than or equal to the proximity threshold
     */
    private boolean isClose(Location A, Location B) {
        int distance = (int) Math.sqrt(Math.pow(A.getLatitude() - B.getLatitude(), 2) + Math.pow(A.getLongitude() - B.getLongitude(), 2));
        return distance <= PROXIMITY_THRESHOLD;
    }
}
