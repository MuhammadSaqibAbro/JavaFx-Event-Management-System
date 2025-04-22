package com.example.javafxapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EventManagementDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Navigation Panel
        VBox navPanel = new VBox(20);
        navPanel.setPadding(new Insets(20));
        navPanel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bbdefb; -fx-border-width: 2px;");
        navPanel.setPrefWidth(280);
        navPanel.setAlignment(Pos.CENTER_RIGHT);  // Align the buttons to the right

        // Add navigation buttons
        Button userRolesBtn = createNavButton("User Roles / Access Control", "/user-icon.png");
        Button attendeeBtn = createNavButton("Attendee / Participant Management", "/attendee-icon.png");
        Button ticketBtn = createNavButton("Ticket / Pass Management", "/ticket-icon.png");
        Button scheduleBtn = createNavButton("Schedule / Agenda Management", "/schedule-icon.png");

        // Set up button actions
        userRolesBtn.setOnAction(e -> openMainPage(primaryStage));
        attendeeBtn.setOnAction(e -> openAttendeeManagementPage(primaryStage));
        ticketBtn.setOnAction(e -> openTicketManagementPage(primaryStage));
        scheduleBtn.setOnAction(e -> openScheduleManagementPage(primaryStage));

        navPanel.getChildren().addAll(userRolesBtn, attendeeBtn, ticketBtn, scheduleBtn);

        // Header
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3);");
        header.setAlignment(Pos.CENTER);

        Text title = new Text("Event Management System");
        title.setFont(Font.font("Arial", 28));
        title.setFill(Color.WHITE);

        header.getChildren().addAll(title);
        header.setSpacing(15);

        // Main Dashboard Area
        VBox dashboard = new VBox(30);
        dashboard.setPadding(new Insets(30));
        dashboard.setAlignment(Pos.TOP_CENTER);
        dashboard.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");

        // Adding a LineChart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Months");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Event Attendance");

        LineChart<Number, Number> eventLineChart = new LineChart<>(xAxis, yAxis);
        eventLineChart.setTitle("Monthly Event Attendance");
        eventLineChart.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-border-color: #bbdefb; -fx-border-radius: 10; -fx-padding: 10;");

        XYChart.Series<Number, Number> attendanceSeries = new XYChart.Series<>();
        attendanceSeries.setName("Attendance Trends");
        attendanceSeries.getData().add(new XYChart.Data<>(1, 200));
        attendanceSeries.getData().add(new XYChart.Data<>(2, 400));
        attendanceSeries.getData().add(new XYChart.Data<>(3, 300));
        attendanceSeries.getData().add(new XYChart.Data<>(4, 500));
        attendanceSeries.getData().add(new XYChart.Data<>(5, 700));

        eventLineChart.getData().add(attendanceSeries);

        // Adding a BarChart with CategoryAxis for X-Axis
        CategoryAxis xCategoryAxis = new CategoryAxis();
        xCategoryAxis.setLabel("Months");

        BarChart<String, Number> eventBarChart = new BarChart<>(xCategoryAxis, yAxis);
        eventBarChart.setTitle("Event Attendance by Month");
        eventBarChart.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-border-color: #bbdefb; -fx-border-radius: 10; -fx-padding: 10;");

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Attendance Data");
        barSeries.getData().add(new XYChart.Data<>("January", 150));
        barSeries.getData().add(new XYChart.Data<>("February", 250));
        barSeries.getData().add(new XYChart.Data<>("March", 350));
        barSeries.getData().add(new XYChart.Data<>("April", 450));
        barSeries.getData().add(new XYChart.Data<>("May", 600));

        eventBarChart.getData().add(barSeries);

        // Adding dashboard cards
        VBox cardSection = new VBox(30);
        cardSection.setAlignment(Pos.CENTER);
        cardSection.getChildren().addAll(eventLineChart, eventBarChart);

        dashboard.getChildren().add(cardSection);

        // Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(15));
        footer.setStyle("-fx-background-color: #4CAF50;");
        footer.setAlignment(Pos.CENTER);
        Text footerText = new Text("\u00a9 2025 Event Management System | Terms | Privacy Policy");
        footerText.setFont(Font.font("Arial", 14));
        footerText.setFill(Color.WHITE);
        footer.getChildren().add(footerText);

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setRight(navPanel);  // Positioning the navigation panel to the right
        mainLayout.setTop(header);
        mainLayout.setCenter(dashboard);
        mainLayout.setBottom(footer);

        // Scene
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setTitle("Event Management Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createNavButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setPrefWidth(240);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Verdana';");

        Image icon = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(25);
        iconView.setFitWidth(25);
        button.setGraphic(iconView);

        return button;
    }

    // Navigation Methods
    private void openMainPage(Stage primaryStage) {
        Main mainPage = new Main();
        try {
            mainPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAttendeeManagementPage(Stage primaryStage) {
        AttendeeManagementApp attendeeManagementPage = new AttendeeManagementApp();
        try {
            attendeeManagementPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTicketManagementPage(Stage primaryStage) {
        TicketManagementApp ticketManagementPage = new TicketManagementApp();
        try {
            ticketManagementPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openScheduleManagementPage(Stage primaryStage) {
        ScheduleManagementApp scheduleManagementPage = new ScheduleManagementApp();
        try {
            scheduleManagementPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
