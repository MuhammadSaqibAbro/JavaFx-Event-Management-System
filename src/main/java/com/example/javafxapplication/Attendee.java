package com.example.javafxapplication;

import java.util.List;

public class Attendee {

    private String id;
    private String name;
    private String contactInfo;
    private List<String> registeredEvents;

    public Attendee(String id, String name, String contactInfo, List<String> registeredEvents) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.registeredEvents = registeredEvents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(List<String> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    @Override
    public String toString() {
        return "Attendee ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Contact: " + contactInfo + "\n" +
                "Registered Events: " + String.join(", ", registeredEvents);
    }
}
