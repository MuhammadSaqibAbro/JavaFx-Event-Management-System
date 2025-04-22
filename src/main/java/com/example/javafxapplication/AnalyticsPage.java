package com.example.javafxapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnalyticsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Header Section
        VBox header = createHeader();

        // Analytics Section
        VBox analyticsBox = createAnalyticsSection();

        // Back Button (Centered)
        Button backButton = new Button("Back to Dashboard");
        backButton.setStyle("-fx-background-color: #1ABC9C; -fx-text-fill: white; -fx-font-size: 16px;");
        backButton.setOnAction(e -> goBackToDashboard(primaryStage));

        // Center the back button using HBox
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.CENTER);
        backButtonBox.setPadding(new Insets(20));

        // Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(header, analyticsBox, backButtonBox);

        // Scene
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setTitle("Analytics - Event Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2c3e50; -fx-padding: 30px; -fx-border-radius: 10px;");

        Text title = new Text("Analytics");
        title.setFont(Font.font("Arial", 50));
        title.setFill(Color.WHITE);

        Text description = new Text("Visualize your event data and insights.");
        description.setFont(Font.font("Arial", 20));
        description.setFill(Color.LIGHTGRAY);

        header.getChildren().addAll(title, description);
        return header;
    }

    private VBox createAnalyticsSection() {
        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Event Type");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Attendees");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Event Attendance Analytics");

        // Adding data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Event Attendance");

        // Sample data
        series.getData().add(new XYChart.Data<>("Event 1", 120));
        series.getData().add(new XYChart.Data<>("Event 2", 80));
        series.getData().add(new XYChart.Data<>("Event 3", 150));

        barChart.getData().add(series);

        // Styling the chart
        barChart.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 10px;");
        barChart.setPrefSize(900, 400);

        VBox analyticsBox = new VBox(20);
        analyticsBox.setAlignment(Pos.CENTER);
        analyticsBox.getChildren().add(barChart);

        return analyticsBox;
    }

    private void goBackToDashboard(Stage primaryStage) {
        EventManagementDashboard dashboard = new EventManagementDashboard();
        try {
            dashboard.start(primaryStage);  // This will show the dashboard again
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
