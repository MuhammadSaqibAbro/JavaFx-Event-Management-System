package com.example.javafxapplication;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
public class NotificationsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root container
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f7fa;");

        // Header Section
        VBox header = createHeader();
        root.setTop(header);

        // Notifications Section
        ScrollPane notificationsScroll = createNotificationsSection();
        root.setCenter(notificationsScroll);

        // Back Button Section
        HBox backButtonSection = createBackButton(primaryStage);
        root.setBottom(backButtonSection);

        // Scene setup
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Notifications");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2c3e50; -fx-padding: 30px; -fx-border-radius: 10px;");

        Text title = new Text("Notifications");
        title.setFont(Font.font("Arial", 50));
        title.setFill(Color.WHITE);

        Text description = new Text("Stay updated with real-time notifications.");
        description.setFont(Font.font("Arial", 20));
        description.setFill(Color.LIGHTGRAY);

        header.getChildren().addAll(title, description);
        return header;
    }

    private ScrollPane createNotificationsSection() {
        VBox notificationsBox = new VBox(30);
        notificationsBox.setAlignment(Pos.TOP_CENTER);
        notificationsBox.setStyle("-fx-padding: 30px;");

        // Adding some predefined notifications
        notificationsBox.getChildren().add(createNotificationCard("Event Scheduled", "Your event has been successfully scheduled.", "/schedule-icon.png"));
        notificationsBox.getChildren().add(createNotificationCard("Ticket Sold", "A new ticket has been sold for your event.", "/ticket-icon.png"));
        notificationsBox.getChildren().add(createNotificationCard("New Attendee", "A new attendee has registered for your event.", "/attendee-icon.png"));

        // ScrollPane to make the notifications section scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(notificationsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private HBox createNotificationCard(String title, String description, String iconPath) {
        HBox notificationCard = new HBox(20);
        notificationCard.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-radius: 15px; -fx-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        notificationCard.setAlignment(Pos.CENTER);
        notificationCard.setPrefWidth(1000);

        // Notification Icon
        Image icon = new Image(getClass().getResourceAsStream(iconPath)); // Load the image from the resources
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(40);
        iconView.setFitHeight(40);
        iconView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 2, 2);");

        // Notification Details
        VBox notificationDetails = new VBox(15);
        notificationDetails.setAlignment(Pos.TOP_LEFT);
        notificationDetails.setStyle("-fx-padding: 15px;");

        Text notificationTitle = new Text(title);
        notificationTitle.setFont(Font.font("Arial", 26));
        notificationTitle.setFill(Color.DARKSLATEGRAY);

        Text notificationDescription = new Text(description);
        notificationDescription.setFont(Font.font("Arial", 16));
        notificationDescription.setFill(Color.DARKGRAY);
        notificationDescription.setWrappingWidth(500);

        notificationDetails.getChildren().addAll(notificationTitle, notificationDescription);

        notificationCard.getChildren().addAll(iconView, notificationDetails);
        return notificationCard;
    }

    private HBox createBackButton(Stage primaryStage) {
        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.CENTER);
        backButtonBox.setStyle("-fx-padding: 20px;");

        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: #1ABC9C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        backButton.setOnAction(e -> {
            // When the back button is clicked, switch to the Event Management Dashboard
            EventManagementDashboard dashboard = new EventManagementDashboard();
            dashboard.start(primaryStage);
        });

        backButtonBox.getChildren().add(backButton);
        return backButtonBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
