package com.example.javafxapplication;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class TicketManagementApp extends Application {

    private final TableView<Ticket> table = new TableView<>();
    private final ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    private final TextField ticketIDField = new TextField();
    private final TextField attendeeNameField = new TextField();
    private final TextField eventDetailsField = new TextField();
    private final TextField statusField = new TextField();

    private Ticket selectedTicket = null;

    private static final String TICKET_FILE = "tickets.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Ticket Management System");

        // Header
        Text header = new Text("Ticket Management System");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(header);
        headerPane.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-padding: 20px;");

        // Table columns for tickets
        TableColumn<Ticket, String> ticketIDColumn = new TableColumn<>("Ticket ID");
        ticketIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketID()));

        TableColumn<Ticket, String> attendeeColumn = new TableColumn<>("Attendee Name");
        attendeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAttendeeName()));

        TableColumn<Ticket, String> eventColumn = new TableColumn<>("Event Details");
        eventColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventDetails()));

        TableColumn<Ticket, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        table.getColumns().addAll(ticketIDColumn, attendeeColumn, eventColumn, statusColumn);
        table.setStyle("-fx-background-color: #f4f4f4; -fx-table-cell-border-color: transparent;");
        table.setOnMouseClicked(e -> selectRow());

        // Load tickets from the file
        loadTicketsFromFile();

        // Buttons
        Button addButton = createStyledButton("Add Ticket", "#4CAF50");
        addButton.setOnAction(e -> addTicket());

        Button updateButton = createStyledButton("Update Ticket", "#FF9800");
        updateButton.setOnAction(e -> updateTicket());

        Button deleteButton = createStyledButton("Delete Ticket", "#F44336");
        deleteButton.setOnAction(e -> deleteTicket());

        // Back Button
        Button backButton = createStyledButton("Back", "#2196F3");
        backButton.setOnAction(e -> openEventManagementDashboard(stage));

        // Input form
        GridPane formLayout = new GridPane();
        formLayout.setVgap(15);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.TOP_LEFT);

        formLayout.add(createStyledLabel("Ticket ID:"), 0, 0);
        formLayout.add(ticketIDField, 1, 0);
        formLayout.add(createStyledLabel("Attendee Name:"), 0, 1);
        formLayout.add(attendeeNameField, 1, 1);
        formLayout.add(createStyledLabel("Event Details:"), 0, 2);
        formLayout.add(eventDetailsField, 1, 2);
        formLayout.add(createStyledLabel("Status:"), 0, 3);
        formLayout.add(statusField, 1, 3);

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setStyle("-fx-padding: 20px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        leftPanel.getChildren().addAll(backButton, formLayout, buttonBox);

        // Main layout
        HBox mainLayout = new HBox(30);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb); -fx-padding: 30px;");
        mainLayout.getChildren().addAll(leftPanel, table);

        VBox root = new VBox(headerPane, mainLayout);

        Scene scene = new Scene(root, 900, 700);
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

    private void loadTicketsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Ticket ticket = new Ticket(data[0], data[1], data[2], data[3]);
                    ticketList.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(ticketList);
    }

    private void saveTicketsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKET_FILE))) {
            for (Ticket ticket : ticketList) {
                writer.write(String.join(",", ticket.getTicketID(), ticket.getAttendeeName(), ticket.getEventDetails(), ticket.getStatus()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTicket() {
        String ticketID = ticketIDField.getText();
        String attendeeName = attendeeNameField.getText();
        String eventDetails = eventDetailsField.getText();
        String status = statusField.getText();

        if (ticketID.isEmpty() || attendeeName.isEmpty() || eventDetails.isEmpty() || status.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        Ticket newTicket = new Ticket(ticketID, attendeeName, eventDetails, status);
        ticketList.add(newTicket);
        saveTicketsToFile();
        clearFields();
    }

    private void updateTicket() {
        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to update!");
            return;
        }

        selectedTicket.setTicketID(ticketIDField.getText());
        selectedTicket.setAttendeeName(attendeeNameField.getText());
        selectedTicket.setEventDetails(eventDetailsField.getText());
        selectedTicket.setStatus(statusField.getText());

        table.refresh();
        saveTicketsToFile();
        clearFields();
    }

    private void deleteTicket() {
        Ticket ticket = table.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            showAlert("Error", "Please select a ticket to delete!");
            return;
        }

        ticketList.remove(ticket);
        saveTicketsToFile();
    }

    private void selectRow() {
        selectedTicket = table.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            ticketIDField.setText(selectedTicket.getTicketID());
            attendeeNameField.setText(selectedTicket.getAttendeeName());
            eventDetailsField.setText(selectedTicket.getEventDetails());
            statusField.setText(selectedTicket.getStatus());
        }
    }

    private void clearFields() {
        ticketIDField.clear();
        attendeeNameField.clear();
        eventDetailsField.clear();
        statusField.clear();
        selectedTicket = null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openEventManagementDashboard(Stage stage) {
        EventManagementDashboard eventDashboard = new EventManagementDashboard();
        try {
            eventDashboard.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
