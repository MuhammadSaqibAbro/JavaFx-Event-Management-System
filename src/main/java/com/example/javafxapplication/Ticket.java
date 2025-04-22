package com.example.javafxapplication;

public class Ticket {
    private String ticketID;
    private String attendeeName;
    private String eventDetails;
    private String status;

    public Ticket(String ticketID, String attendeeName, String eventDetails, String status) {
        this.ticketID = ticketID;
        this.attendeeName = attendeeName;
        this.eventDetails = eventDetails;
        this.status = status;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
