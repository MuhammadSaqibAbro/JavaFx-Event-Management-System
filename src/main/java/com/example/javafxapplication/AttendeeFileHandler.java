package com.example.javafxapplication;

import java.io.*;
import java.util.*;

public class AttendeeFileHandler {

    private static final String FILE_PATH = AttendeeFileHandler.class.getResource("/attendees.txt").getPath();

    // Method to load attendees from the file
    public static List<Attendee> loadAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Each line in the file should have attendee ID, name, email, and events (comma-separated)
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String attendeeId = data[0].trim();
                    String attendeeName = data[1].trim();
                    String attendeeEmail = data[2].trim();
                    List<String> events = Arrays.asList(data[3].split(";"));

                    Attendee attendee = new Attendee(attendeeId, attendeeName, attendeeEmail, events);
                    attendees.add(attendee);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading attendees from file: " + e.getMessage());
        }
        return attendees;
    }

    // Method to save an attendee to the file
    public static void saveAttendee(Attendee attendee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(attendee.getId() + "," + attendee.getName() + "," + attendee.getContactInfo() + "," + String.join(";", attendee.getRegisteredEvents()) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving attendee: " + e.getMessage());
        }
    }

    // Method to remove an attendee from the file
    public static void removeAttendee(Attendee attendee) {
        List<Attendee> attendees = loadAttendees();
        attendees.removeIf(a -> a.getId().equals(attendee.getId()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Attendee a : attendees) {
                writer.write(a.getId() + "," + a.getName() + "," + a.getContactInfo() + "," + String.join(";", a.getRegisteredEvents()) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving attendees after removal: " + e.getMessage());
        }
    }
}
