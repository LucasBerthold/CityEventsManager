/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Lucas
 */

import Models.Event;
import Models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventManager {
    private List<Event> events;
    private User currentUser;

    public EventManager(User user) {
        this.events = new ArrayList<>();
        this.currentUser = user;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getUpcomingEvents() {
        return events.stream()
                .filter(e -> !e.isPast())
                .sorted(Comparator.comparing(Event::getDateTime))
                .toList();
    }

    public List<Event> getPastEvents() {
        return events.stream()
                .filter(Event::isPast)
                .sorted(Comparator.comparing(Event::getDateTime))
                .toList();
    }

    public List<Event> getCurrentEvents() {
        return events.stream()
                .filter(Event::isOngoing)
                .toList();
    }

    public void joinEvent(Event event) {
        event.addParticipant(currentUser);
    }

    public void cancelParticipation(Event event) {
        event.removeParticipant(currentUser);
    }

    public List<Event> getUserEvents() {
        return events.stream()
                .filter(e -> e.getParticipants().contains(currentUser))
                .toList();
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}

