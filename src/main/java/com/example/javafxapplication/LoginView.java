package com.example.javafxapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class LoginView {
    private final VBox loginPane;

    public LoginView(Stage stage) {
        loginPane = new VBox(20);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setPadding(new Insets(30));
        loginPane.setBackground(new Background(new BackgroundFill(
                Color.web("#E3F2FD"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Header
        Label headerLabel = new Label("User Roles and Authentication");
        headerLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1565C0;");
        headerLabel.setFont(Font.font("Arial"));

        // Username and Password fields
        Label lblUsername = new Label("Username:");
        lblUsername.setStyle("-fx-text-fill: #0D47A1; -fx-font-size: 14px;");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter Username");
        txtUsername.setStyle("-fx-background-color: white; -fx-border-color: #64B5F6; -fx-border-radius: 5px; -fx-padding: 8px; -fx-font-size: 14px;");
        txtUsername.setPrefWidth(250);

        Label lblPassword = new Label("Password:");
        lblPassword.setStyle("-fx-text-fill: #0D47A1; -fx-font-size: 14px;");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter Password");
        txtPassword.setStyle("-fx-background-color: white; -fx-border-color: #64B5F6; -fx-border-radius: 5px; -fx-padding: 8px; -fx-font-size: 14px;");
        txtPassword.setPrefWidth(250);

        // Login Button
        Button btnLogin = new Button("Login");
        btnLogin.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5px;");
        btnLogin.setPrefWidth(250);

        // Button hover effect
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5px;"));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5px;"));

        // Back Button
        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 5px;");
        btnBack.setPrefWidth(250);
        btnBack.setOnAction(e -> {
            // Navigate back to EventManagementDashboard
            EventManagementDashboard eventManagementDashboard = new EventManagementDashboard();
            try {
                eventManagementDashboard.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Login action
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: #D32F2F; -fx-font-size: 14px;");

        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = authenticateUser(username, password);

            if (role != null) {
                lblMessage.setText("Login successful! Role: " + role);
                lblMessage.setStyle("-fx-text-fill: #388E3C; -fx-font-size: 14px;");
                openRoleDashboard(stage, role);
            } else {
                lblMessage.setText("Invalid credentials. Try again.");
            }
        });

        // Adding components to the login pane
        loginPane.getChildren().addAll(headerLabel, lblUsername, txtUsername, lblPassword, txtPassword, btnLogin, btnBack, lblMessage);
    }

    public VBox getLoginPane() {
        return loginPane;
    }

    private String authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("users.txt"))
        ))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return parts[2]; // Return role
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openRoleDashboard(Stage stage, String role) {
        switch (role) {
            case "Manager":
                new ManagerDashboard(stage);
                break;
            case "Event Coordinator":
                new EventCoordinatorDashboard(stage);
                break;
            case "Support Operator":
                new SupportOperatorDashboard(stage);
                break;
            default:
                VBox unknownRolePane = new VBox(10);
                unknownRolePane.setAlignment(Pos.CENTER);
                unknownRolePane.setPadding(new Insets(30));
                unknownRolePane.setBackground(new Background(new BackgroundFill(
                        Color.web("#FBE9E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                Label unknownRoleLabel = new Label("Unknown role.");
                unknownRoleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #D32F2F; -fx-font-weight: bold;");
                Button btnLogout = new Button("Logout");
                btnLogout.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
                btnLogout.setOnAction(e -> stage.setScene(new Scene(new LoginView(stage).getLoginPane(), 400, 300)));
                unknownRolePane.getChildren().addAll(unknownRoleLabel, btnLogout);
                stage.setScene(new Scene(unknownRolePane, 600, 500));
                break;
        }
    }
}
