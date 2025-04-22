package com.example.javafxapplication;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpcomingEventsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root container with a gradient background
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f7fa;");

        // Header Section
        VBox header = createHeader();
        root.setTop(header);

        // Events Section
        ScrollPane eventsScroll = createEventsSection();
        root.setCenter(eventsScroll);

        // Back Button Section
        HBox backButtonBox = createBackButton(primaryStage);
        root.setBottom(backButtonBox);

        // Scene setup
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Upcoming Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2c3e50; -fx-padding: 30px; -fx-border-radius: 10px;");

        Text title = new Text("Upcoming Events");
        title.setFont(Font.font("Arial", 50));
        title.setFill(Color.WHITE);

        Text description = new Text("Stay updated with our upcoming events. Don't miss out!");
        description.setFont(Font.font("Arial", 20));
        description.setFill(Color.LIGHTGRAY);

        header.getChildren().addAll(title, description);
        return header;
    }

    private ScrollPane createEventsSection() {
        VBox eventsBox = new VBox(30);
        eventsBox.setAlignment(Pos.TOP_CENTER);
        eventsBox.setStyle("-fx-padding: 30px;");

        // Event 1
        eventsBox.getChildren().add(createEventCard("Music Concert", "January 20, 2025", "Join us for an unforgettable night of live music!", "event1.jpg"));
        // Event 2
        eventsBox.getChildren().add(createEventCard("Tech Conference", "February 5, 2025", "Get ready for insightful sessions and networking with industry leaders.", "event2.jpg"));
        // Event 3
        eventsBox.getChildren().add(createEventCard("Food Festival", "March 15, 2025", "A culinary journey with food trucks, gourmet chefs, and local delicacies.", "event3.jpg"));

        // ScrollPane to make the events section scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(eventsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private HBox createEventCard(String title, String date, String description, String imageUrl) {
        HBox eventCard = new HBox(20);
        eventCard.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-radius: 15px; -fx-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        eventCard.setAlignment(Pos.CENTER);
        eventCard.setPrefWidth(1000);

        // Event Image with rounded corners
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/" + imageUrl)));
        imageView.setFitWidth(220);
        imageView.setFitHeight(140);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 2, 2);");

        // Event Details
        VBox eventDetails = new VBox(15);
        eventDetails.setAlignment(Pos.TOP_LEFT);
        eventDetails.setStyle("-fx-padding: 15px;");

        Text eventTitle = new Text(title);
        eventTitle.setFont(Font.font("Arial", 26));
        eventTitle.setFill(Color.DARKSLATEGRAY);

        Text eventDate = new Text(date);
        eventDate.setFont(Font.font("Arial", 18));
        eventDate.setFill(Color.GRAY);

        Text eventDescription = new Text(description);
        eventDescription.setFont(Font.font("Arial", 16));
        eventDescription.setFill(Color.DARKGRAY);
        eventDescription.setWrappingWidth(500);

        eventDetails.getChildren().addAll(eventTitle, eventDate, eventDescription);

        eventCard.getChildren().addAll(imageView, eventDetails);
        return eventCard;
    }

    private HBox createBackButton(Stage primaryStage) {
        // Create Back Button
        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: #1ABC9C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-radius: 5px;");
        backButton.setOnAction(e -> {
            // Create and show the EventManagementDashboard when the back button is clicked
            EventManagementDashboard dashboardPage = new EventManagementDashboard();
            dashboardPage.start(primaryStage);
        });

        // Center the back button using HBox
        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.CENTER);
        backButtonBox.getChildren().add(backButton);
        backButtonBox.setStyle("-fx-padding: 20px;");

        return backButtonBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
