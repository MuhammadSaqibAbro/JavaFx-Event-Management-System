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

public class ScheduleManagementApp extends Application {

    private final TableView<Event> table = new TableView<>();
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();
    private final TextField eventNameField = new TextField();
    private final TextField startTimeField = new TextField();
    private final TextField endTimeField = new TextField();
    private final TextField descriptionField = new TextField();
    private final ComboBox<String> scheduleTypeComboBox = new ComboBox<>();
    private Event selectedEvent = null;

    private static final String SCHEDULE_FILE = "schedule.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Schedule Management System");

        // Header with Back Button
        Button backButton = createStyledButton("Back", "#F44336");
        backButton.setOnAction(e -> stage.close()); // Action to close the application or go back to previous screen

        Text header = new Text("Schedule Management System");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(header, backButton);
        headerPane.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-padding: 20px;");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        // Table columns for events
        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Event, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));

        TableColumn<Event, String> endTimeColumn = new TableColumn<>("End Time");
        endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));

        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        TableColumn<Event, String> scheduleTypeColumn = new TableColumn<>("Schedule Type");
        scheduleTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getScheduleType()));

        table.getColumns().addAll(eventNameColumn, startTimeColumn, endTimeColumn, descriptionColumn, scheduleTypeColumn);
        table.setStyle("-fx-background-color: #f4f4f4; -fx-table-cell-border-color: transparent;");
        table.setOnMouseClicked(e -> selectRow());

        // Load events from the file
        loadEventsFromFile();
        // Back Button Action to Navigate to EventManagementDashboard
        backButton.setOnAction(e -> {
            EventManagementDashboard eventManagementDashboard = new EventManagementDashboard();
            try {
                eventManagementDashboard.start(stage); // Launch EventManagementDashboard
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });


        // Buttons
        Button addButton = createStyledButton("Add Event", "#4CAF50");
        addButton.setOnAction(e -> addEvent());

        Button updateButton = createStyledButton("Update Event", "#FF9800");
        updateButton.setOnAction(e -> updateEvent());

        Button deleteButton = createStyledButton("Delete Event", "#F44336");
        deleteButton.setOnAction(e -> deleteEvent());

        Button loadDailyButton = createStyledButton("Load Daily Schedule", "#2196F3");
        loadDailyButton.setOnAction(e -> loadSchedule("Daily"));

        Button loadWeeklyButton = createStyledButton("Load Weekly Schedule", "#2196F3");
        loadWeeklyButton.setOnAction(e -> loadSchedule("Weekly"));

        // Event input form
        GridPane formLayout = new GridPane();
        formLayout.setVgap(15);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.TOP_LEFT);

        formLayout.add(createStyledLabel("Event Name:"), 0, 0);
        formLayout.add(eventNameField, 1, 0);
        formLayout.add(createStyledLabel("Start Time (HH:mm):"), 0, 1);
        formLayout.add(startTimeField, 1, 1);
        formLayout.add(createStyledLabel("End Time (HH:mm):"), 0, 2);
        formLayout.add(endTimeField, 1, 2);
        formLayout.add(createStyledLabel("Description:"), 0, 3);
        formLayout.add(descriptionField, 1, 3);
        formLayout.add(createStyledLabel("Schedule Type:"), 0, 4);
        formLayout.add(scheduleTypeComboBox, 1, 4);

        scheduleTypeComboBox.getItems().addAll("Daily", "Weekly");
        scheduleTypeComboBox.setValue("Daily");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, loadDailyButton, loadWeeklyButton);

        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setStyle("-fx-padding: 20px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        leftPanel.getChildren().addAll(formLayout, buttonBox);

        // Main layout
        HBox mainLayout = new HBox(30);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb); -fx-padding: 30px;");
        mainLayout.getChildren().addAll(leftPanel, table);

        VBox root = new VBox(headerPane, mainLayout);

        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
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

    private void loadEventsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Event event = new Event(data[0], data[1], data[2], data[3], data[4]);
                    eventList.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(eventList);
    }

    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULE_FILE))) {
            for (Event event : eventList) {
                writer.write(String.join(",", event.getName(), event.getStartTime(), event.getEndTime(), event.getDescription(), event.getScheduleType()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEvent() {
        String name = eventNameField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String description = descriptionField.getText();
        String scheduleType = scheduleTypeComboBox.getValue();

        if (name.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        Event newEvent = new Event(name, startTime, endTime, description, scheduleType);
        eventList.add(newEvent);
        saveEventsToFile();
        clearFields();
    }

    private void updateEvent() {
        if (selectedEvent == null) {
            showAlert("Error", "Please select an event to update!");
            return;
        }

        selectedEvent.setName(eventNameField.getText());
        selectedEvent.setStartTime(startTimeField.getText());
        selectedEvent.setEndTime(endTimeField.getText());
        selectedEvent.setDescription(descriptionField.getText());
        selectedEvent.setScheduleType(scheduleTypeComboBox.getValue());

        table.refresh();
        saveEventsToFile();
        clearFields();
    }

    private void deleteEvent() {
        Event event = table.getSelectionModel().getSelectedItem();
        if (event == null) {
            showAlert("Error", "Please select an event to delete!");
            return;
        }

        eventList.remove(event);
        saveEventsToFile();
    }

    private void loadSchedule(String scheduleType) {
        ObservableList<Event> filteredList = FXCollections.observableArrayList();
        for (Event event : eventList) {
            if (event.getScheduleType().equalsIgnoreCase(scheduleType)) {
                filteredList.add(event);
            }
        }
        table.setItems(filteredList);
    }

    private void selectRow() {
        selectedEvent = table.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventNameField.setText(selectedEvent.getName());
            startTimeField.setText(selectedEvent.getStartTime());
            endTimeField.setText(selectedEvent.getEndTime());
            descriptionField.setText(selectedEvent.getDescription());
            scheduleTypeComboBox.setValue(selectedEvent.getScheduleType());
        }
    }

    private void clearFields() {
        eventNameField.clear();
        startTimeField.clear();
        endTimeField.clear();
        descriptionField.clear();
        scheduleTypeComboBox.setValue("Daily");
        selectedEvent = null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
