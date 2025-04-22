package com.example.javafxapplication;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

public class AttendeeManagementApp extends Application {

    private static final String EVENT_FILE_PATH = "events.txt";  // Path to the events file
    private static final String ATTENDEE_FILE_PATH = "attendees.txt"; // Path to the attendees file

    private TextField attendeeIdField, attendeeNameField, attendeeEmailField;
    private ObservableList<String> eventNames;  // List of event names from the events.txt file
    private ComboBox<String> eventComboBox;  // ComboBox for event selection
    private TableView<Attendee> attendeeTable;  // Table to display attendees
    private ObservableList<Attendee> attendees;  // List to hold attendees

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        eventNames = FXCollections.observableArrayList();
        attendees = FXCollections.observableArrayList();
        loadEventsFromFile();  // Load events from the file
        loadAttendeesFromFile();  // Load attendees from the file

        primaryStage.setTitle("Attendee Management");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Header Section
        Text header = new Text("Attendee Management");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(header);
        headerPane.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-padding: 20px;");

        // Form Section
        GridPane formLayout = new GridPane();
        formLayout.setVgap(15);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.TOP_LEFT);

        attendeeIdField = new TextField();
        attendeeNameField = new TextField();
        attendeeEmailField = new TextField();

        eventComboBox = new ComboBox<>(eventNames);
        eventComboBox.setPromptText("Select Event");

        Button addAttendeeButton = createStyledButton("Add Attendee", "#4CAF50");
        Button modifyAttendeeButton = createStyledButton("Modify Attendee", "#FF9800");
        Button deleteAttendeeButton = createStyledButton("Delete Attendee", "#F44336");

        addAttendeeButton.setOnAction(e -> addAttendee());
        modifyAttendeeButton.setOnAction(e -> modifyAttendee());
        deleteAttendeeButton.setOnAction(e -> deleteAttendee());

        formLayout.add(createStyledLabel("Attendee ID:"), 0, 0);
        formLayout.add(attendeeIdField, 1, 0);
        formLayout.add(createStyledLabel("Name:"), 0, 1);
        formLayout.add(attendeeNameField, 1, 1);
        formLayout.add(createStyledLabel("Email:"), 0, 2);
        formLayout.add(attendeeEmailField, 1, 2);
        formLayout.add(createStyledLabel("Event:"), 0, 3);
        formLayout.add(eventComboBox, 1, 3);

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(addAttendeeButton, modifyAttendeeButton, deleteAttendeeButton);

        // Table Section
        attendeeTable = new TableView<>(attendees);
        TableColumn<Attendee, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Attendee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Attendee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo()));

        TableColumn<Attendee, String> eventColumn = new TableColumn<>("Event");
        eventColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegisteredEvents().get(0)));

        attendeeTable.getColumns().addAll(idColumn, nameColumn, emailColumn, eventColumn);
        attendeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        attendeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                attendeeIdField.setText(newSelection.getId());
                attendeeNameField.setText(newSelection.getName());
                attendeeEmailField.setText(newSelection.getContactInfo());
                eventComboBox.setValue(newSelection.getRegisteredEvents().get(0));
            }
        });

        // Back Button
        Button backButton = createStyledButton("Back", "#2196F3");
        backButton.setOnAction(e -> goToEventManagementDashboard(primaryStage));

        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setStyle("-fx-padding: 20px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        leftPanel.getChildren().addAll(backButton, formLayout, buttonBox);

        // Main layout
        HBox mainLayout = new HBox(30);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb); -fx-padding: 30px;");
        mainLayout.getChildren().addAll(leftPanel, attendeeTable);

        VBox root = new VBox(headerPane, mainLayout);

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        return button;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        return label;
    }

    private void goToEventManagementDashboard(Stage primaryStage) {
        EventManagementDashboard dashboard = new EventManagementDashboard();
        dashboard.start(primaryStage);
    }

    private void loadEventsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EVENT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                eventNames.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading events: " + e.getMessage());
        }
    }

    private void loadAttendeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                List<String> events = Collections.singletonList(parts[3]);
                attendees.add(new Attendee(id, name, email, events));
            }
        } catch (IOException e) {
            System.out.println("Error loading attendees: " + e.getMessage());
        }
    }

    private void saveAttendeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ATTENDEE_FILE_PATH))) {
            for (Attendee attendee : attendees) {
                writer.write(String.join(",", attendee.getId(), attendee.getName(), attendee.getContactInfo(), attendee.getRegisteredEvents().get(0)));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving attendees: " + e.getMessage());
        }
    }

    private void addAttendee() {
        String id = attendeeIdField.getText();
        String name = attendeeNameField.getText();
        String email = attendeeEmailField.getText();
        String event = eventComboBox.getValue();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || event == null) {
            showAlert("Please fill all fields.");
            return;
        }

        Attendee newAttendee = new Attendee(id, name, email, Collections.singletonList(event));
        attendees.add(newAttendee);
        saveAttendeesToFile();
        clearFields();
    }

    private void modifyAttendee() {
        Attendee selected = attendeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an attendee to modify.");
            return;
        }

        String id = attendeeIdField.getText();
        String name = attendeeNameField.getText();
        String email = attendeeEmailField.getText();
        String event = eventComboBox.getValue();

        selected.setName(name);
        selected.setContactInfo(email);
        selected.setRegisteredEvents(Collections.singletonList(event));
        attendeeTable.refresh();
        saveAttendeesToFile();
        clearFields();
    }

    private void deleteAttendee() {
        Attendee selected = attendeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an attendee to delete.");
            return;
        }

        attendees.remove(selected);
        saveAttendeesToFile();
        clearFields();
    }

    private void clearFields() {
        attendeeIdField.clear();
        attendeeNameField.clear();
        attendeeEmailField.clear();
        eventComboBox.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Attendee {
        private final String id;
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
    }
}









