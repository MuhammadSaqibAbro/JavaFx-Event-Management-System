package com.example.javafxapplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManagerDashboard {
    public ManagerDashboard(Stage stage) {
        // Root container using BorderPane for explicit top placement of header
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #E3F2FD, #BBDEFB); -fx-padding: 20px;");

        // Header container with a stylish gradient and padding
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: linear-gradient(to right, #1565C0, #1E88E5); -fx-padding: 20px;");

        // Label for dashboard title with stylish text and shadow effect
        Label lblWelcome = new Label("Manager Dashboard");
        lblWelcome.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white; "
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 4, 4);");

        // Add the title to the header
        header.getChildren().add(lblWelcome);

        // Create buttons with modern color scheme and effects
        Button btnAddEvent = createStyledButton("Add Event");
        Button btnManageUsers = createStyledButton("Manage Users");
        Button btnLogout = createStyledButton("Logout");

        // Button actions
        btnAddEvent.setOnAction(e -> new AddEventDashboard(stage));
        btnManageUsers.setOnAction(e -> new ManageUsersDashboard(stage));

        // Logout button action: Go back to the Main class
        btnLogout.setOnAction(e -> {
            Main main = new Main();
            main.start(stage); // Restart the application by calling Main
        });

        // Add all components to the root container
        VBox buttonsContainer = new VBox(20);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(btnAddEvent, btnManageUsers, btnLogout);

        // Add header and buttons container to BorderPane
        root.setTop(header);
        root.setCenter(buttonsContainer);

        // Set scene for the stage with rounded corners and custom shadow effect
        Scene scene = new Scene(root, 700, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Manager Dashboard");
        stage.setResizable(false);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;");

        // Add hover effect for better user experience
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-radius: 25; -fx-border-radius: 25; -fx-padding: 10px;"));

        // Add shadow effect for depth
        button.setEffect(new DropShadow(10, Color.GRAY));

        return button;
    }
}
