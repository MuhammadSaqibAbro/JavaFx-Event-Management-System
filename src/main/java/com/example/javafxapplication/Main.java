package com.example.javafxapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView(stage); // Create a new instance of LoginView
        stage.setTitle("Login");
        stage.setScene(new Scene(loginView.getLoginPane(), 600, 500)); // Set the initial scene
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
