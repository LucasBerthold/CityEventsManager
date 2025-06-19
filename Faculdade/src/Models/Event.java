/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Lucas
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String address;
    private EventCategory category;
    private LocalDateTime dateTime;
    private String description;
    private List<User> participants;

    public Event(String name, String address, EventCategory category,
                 LocalDateTime dateTime, String description) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.dateTime = dateTime;
        this.description = description;
        this.participants = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public EventCategory getCategory() { return category; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public List<User> getParticipants() { return participants; }

    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    public boolean isOngoing() {
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isBefore(now) && dateTime.plusHours(2).isAfter(now);
    }

    public boolean isPast() {
        return dateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return name + " | " + category + " | " + dateTime + " | " + address + "\n" + description;
    }
}
