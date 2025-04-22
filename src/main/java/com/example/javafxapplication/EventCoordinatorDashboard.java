package com.example.javafxapplication;



import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventCoordinatorDashboard {
    public EventCoordinatorDashboard(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label lblWelcome = new Label("Event Coordinator Dashboard");
        Button btnManageEvents = new Button("Manage Events");
        Button btnManageAttendees = new Button("Manage Attendees");
        Button btnLogout = new Button("Logout");

        btnLogout.setOnAction(e -> stage.setScene(new Scene(new LoginView(stage).getLoginPane(), 400, 300)));

        root.getChildren().addAll(lblWelcome, btnManageEvents, btnManageAttendees, btnLogout);

        stage.setScene(new Scene(root, 400, 300));
    }
}
