package com.example.javafxapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddEventDashboard {
    private static final String FILE_NAME = "events.txt";
    private final TableView<Event> eventTable;

    public AddEventDashboard(Stage stage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #E3F2FD, #BBDEFB);");

        // Left side: Form and buttons
        VBox leftPane = new VBox(20);
        leftPane.setPadding(new Insets(20));
        leftPane.setAlignment(Pos.TOP_CENTER);
        leftPane.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #BBDEFB; -fx-border-width: 2px; -fx-border-radius: 10px;");

        Label lblFormTitle = new Label("Add Event");
        lblFormTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1565C0;");

        TextField txtEventName = new TextField();
        txtEventName.setPromptText("Event Name");
        txtEventName.setStyle("-fx-background-color: white; -fx-border-color: #BBDEFB; -fx-border-radius: 5px; -fx-padding: 10px;");

        TextField txtEventDate = new TextField();
        txtEventDate.setPromptText("Event Date (YYYY-MM-DD)");
        txtEventDate.setStyle("-fx-background-color: white; -fx-border-color: #BBDEFB; -fx-border-radius: 5px; -fx-padding: 10px;");

        Button btnAddEvent = createStyledButton("Add Event", "#1E88E5", "#1565C0");
        Button btnBack = createStyledButton("Back", "#34495E", "#2C3E50");
        Button btnLogout = createStyledButton("Logout", "#E74C3C", "#C0392B");

        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: #E74C3C; -fx-font-weight: bold;");

        btnAddEvent.setOnAction(e -> {
            String eventName = txtEventName.getText().trim();
            String eventDate = txtEventDate.getText().trim();

            if (!eventName.isEmpty() && !eventDate.isEmpty()) {
                addEventToFile(eventName, eventDate);
                lblMessage.setText("Event added successfully!");
                lblMessage.setStyle("-fx-text-fill: #2ECC71; -fx-font-weight: bold;");
                txtEventName.clear();
                txtEventDate.clear();
                loadEvents();
            } else {
                lblMessage.setText("Please fill in all fields.");
                lblMessage.setStyle("-fx-text-fill: #E74C3C; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> new ManagerDashboard(stage));
        btnLogout.setOnAction(e -> new ManagerDashboard(stage));

        leftPane.getChildren().addAll(lblFormTitle, txtEventName, txtEventDate, btnAddEvent, lblMessage, btnBack, btnLogout);

        // Right side: Table
        eventTable = new TableView<>();
        eventTable.setEditable(true);
        eventTable.setStyle("-fx-background-color: white; -fx-border-color: #BBDEFB;");

        TableColumn<Event, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        TableColumn<Event, String> dateColumn = new TableColumn<>("Event Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(200);

        TableColumn<Event, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setPrefWidth(200);
        actionColumn.setCellFactory(col -> createActionButtons());

        eventTable.getColumns().addAll(nameColumn, dateColumn, actionColumn);

        VBox rightPane = new VBox(10, eventTable);
        rightPane.setAlignment(Pos.TOP_CENTER);
        rightPane.setPadding(new Insets(20));

        // Add left and right panes to root
        root.setLeft(leftPane);
        root.setCenter(rightPane);

        // Set the scene
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Add Event Dashboard");
        stage.setResizable(false);
        loadEvents();
    }

    private Button createStyledButton(String text, String baseColor, String hoverColor) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;"));
        return button;
    }

    private void addEventToFile(String eventName, String eventDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(eventName + "," + eventDate);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Event> readEventsFromFile() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    events.add(new Event(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    private void loadEvents() {
        eventTable.getItems().clear();
        eventTable.getItems().addAll(readEventsFromFile());
    }

    private TableCell<Event, Void> createActionButtons() {
        return new TableCell<>() {
            private final Button btnUpdate = createStyledButton("Update", "#1E88E5", "#1565C0");
            private final Button btnDelete = createStyledButton("Delete", "#E74C3C", "#C0392B");
            private final HBox actionButtons = new HBox(10, btnUpdate, btnDelete);

            {
                actionButtons.setAlignment(Pos.CENTER);
                btnUpdate.setOnAction(e -> handleUpdateAction(getTableRow().getItem()));
                btnDelete.setOnAction(e -> handleDeleteAction(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButtons);
                }
            }
        };
    }

    private void handleUpdateAction(Event event) {
        TextInputDialog dialog = new TextInputDialog(event.getName());
        dialog.setTitle("Update Event");
        dialog.setHeaderText("Update Event Name");
        dialog.setContentText("New Event Name:");

        dialog.showAndWait().ifPresent(newName -> {
            event.setName(newName);
            saveEventsToFile();
            loadEvents();
        });
    }

    private void handleDeleteAction(Event event) {
        eventTable.getItems().remove(event);
        saveEventsToFile();
        loadEvents();
    }

    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Event event : eventTable.getItems()) {
                writer.write(event.getName() + "," + event.getDate());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Event {
        private String name;
        private String date;

        public Event(String name, String date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}