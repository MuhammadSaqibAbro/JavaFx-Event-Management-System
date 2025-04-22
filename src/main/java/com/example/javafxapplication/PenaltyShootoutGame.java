package com.example.javafxapplication;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class PenaltyShootoutGame extends Application {

    private String playerTeam = "";
    private String opponentTeam = "";
    private int playerScore = 0;
    private int opponentScore = 0;
    private int shotsTaken = 0;
    private final int MAX_SHOTS = 5;

    private Label scoreLabel;
    private Label statusLabel;
    private Label timerLabel;
    private Label shotsLeftLabel;
    private Circle ball;
    private ImageView goalKeeper;
    private ImageView goal;
    private Label resultLabel; // Label to display result on the goal field
    private Random random;
    private Timeline gameTimer;
    private int timeRemaining = 60; // 60 seconds game time

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        random = new Random();
        primaryStage.setTitle("Penalty Shootout Game");

        // Main menu
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-background-color: #102031; -fx-padding: 20;");
        Label titleLabel = new Label("Penalty Shootout Game");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.WHITE);

        Button teamRedButton = new Button("Play as Team Red");
        Button teamBlueButton = new Button("Play as Team Blue");
        styleButton(teamRedButton);
        styleButton(teamBlueButton);

        teamRedButton.setOnAction(e -> {
            playerTeam = "Team Red";
            opponentTeam = "Team Blue";
            startGame(primaryStage);
        });

        teamBlueButton.setOnAction(e -> {
            playerTeam = "Team Blue";
            opponentTeam = "Team Red";
            startGame(primaryStage);
        });

        menu.getChildren().addAll(titleLabel, teamRedButton, teamBlueButton);
        Scene menuScene = new Scene(menu, 800, 600);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Scoreboard and Timer
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #204060; -fx-padding: 10;");
        scoreLabel = new Label(getScoreText());
        scoreLabel.setFont(new Font("Arial", 24));
        scoreLabel.setTextFill(Color.WHITE);

        timerLabel = new Label("Time: 60s");
        timerLabel.setFont(new Font("Arial", 24));
        timerLabel.setTextFill(Color.WHITE);

        shotsLeftLabel = new Label("Shots Left: " + (MAX_SHOTS - shotsTaken));
        shotsLeftLabel.setFont(new Font("Arial", 24));
        shotsLeftLabel.setTextFill(Color.WHITE);

        topBar.getChildren().addAll(scoreLabel, timerLabel, shotsLeftLabel);
        root.setTop(topBar);

        // Game area
        StackPane gameArea = new StackPane();
        gameArea.setStyle("-fx-background-color: #006400;");
        goal = new ImageView(new Image(getClass().getResource("field.png").toExternalForm()));
        goal.setFitWidth(800);
        goal.setFitHeight(400);

        ball = new Circle(15, Color.WHITE);
        ball.setTranslateY(150);

        goalKeeper = new ImageView(new Image(getClass().getResource("goalkeeper.png").toExternalForm()));
        goalKeeper.setFitWidth(100);
        goalKeeper.setFitHeight(100);
        goalKeeper.setTranslateY(-150);

        resultLabel = new Label("");
        resultLabel.setFont(new Font("Arial", 36));
        resultLabel.setTextFill(Color.YELLOW);
        resultLabel.setTranslateY(-100); // Position the label in the center of the goal field

        gameArea.getChildren().addAll(goal, ball, goalKeeper, resultLabel);
        root.setCenter(gameArea);

        // Controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.setStyle("-fx-padding: 10;");

        Button leftButton = new Button("Shoot Left");
        Button centerButton = new Button("Shoot Center");
        Button rightButton = new Button("Shoot Right");
        styleButton(leftButton);
        styleButton(centerButton);
        styleButton(rightButton);

        leftButton.setOnAction(e -> takeShot("left"));
        centerButton.setOnAction(e -> takeShot("center"));
        rightButton.setOnAction(e -> takeShot("right"));

        controls.getChildren().addAll(leftButton, centerButton, rightButton);

        // Status
        statusLabel = new Label("Take your shot!");
        statusLabel.setFont(new Font("Arial", 18));
        statusLabel.setTextFill(Color.WHITE);

        VBox bottomArea = new VBox(10, controls, statusLabel);
        bottomArea.setAlignment(Pos.CENTER);
        bottomArea.setStyle("-fx-background-color: #204060; -fx-padding: 10;");
        root.setBottom(bottomArea);

        Scene gameScene = new Scene(root, 800, 600);
        primaryStage.setScene(gameScene);
        primaryStage.show();

        startTimer();
    }

    private void startTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
            if (timeRemaining <= 0) {
                gameTimer.stop();
                statusLabel.setText("Time's up! " + getResultText());
            }
        }));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private void takeShot(String direction) {
        if (shotsTaken >= MAX_SHOTS || timeRemaining <= 0) {
            statusLabel.setText("Game Over! " + getResultText());
            return;
        }

        shotsTaken++;
        shotsLeftLabel.setText("Shots Left: " + (MAX_SHOTS - shotsTaken));
        animateShot(direction);
    }

    private void animateShot(String direction) {
        double ballTargetX = getTargetPosition(direction);
        double keeperTargetX = getGoalKeeperPosition(randomDirection());

        TranslateTransition ballMove = new TranslateTransition(Duration.seconds(1), ball);
        ballMove.setToX(ballTargetX);
        ballMove.setToY(-50);

        TranslateTransition keeperMove = new TranslateTransition(Duration.seconds(1), goalKeeper);
        keeperMove.setToX(keeperTargetX);

        ParallelTransition sequence = new ParallelTransition(ballMove, keeperMove);
        sequence.setOnFinished(e -> {
            String keeperDirection = getDirectionFromPosition(keeperTargetX);
            if (isGoal(direction, keeperDirection)) {
                playerScore++;
                resultLabel.setText("Goal!"); // Display "Goal!" immediately after the shot
            } else {
                resultLabel.setText("Saved!"); // Display "Saved!" immediately after the shot
            }

            // Set a small delay before updating the game state (e.g., opponent's turn)
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                opponentTakesShot();
                scoreLabel.setText(getScoreText());

                if (shotsTaken >= MAX_SHOTS) {
                    statusLabel.setText("Game Over! " + getResultText());
                    gameTimer.stop();
                } else {
                    resetPositions();
                }
            }));
            delay.play(); // Play the delay before moving to the next turn

        });
        sequence.play();
    }


    private void opponentTakesShot() {
        if (random.nextBoolean()) {
            opponentScore++;
        }
    }

    private void resetPositions() {
        ball.setTranslateX(0);
        ball.setTranslateY(150);
        goalKeeper.setTranslateX(0);
        resultLabel.setText(""); // Clear the result label
    }

    private String getScoreText() {
        return playerTeam + " " + playerScore + " - " + opponentScore + " " + opponentTeam;
    }

    private String getResultText() {
        if (playerScore > opponentScore) {
            return playerTeam + " Wins!";
        } else if (playerScore < opponentScore) {
            return opponentTeam + " Wins!";
        } else {
            return "It's a Draw!";
        }
    }

    private double getTargetPosition(String direction) {
        switch (direction) {
            case "left":
                return -200;
            case "right":
                return 200;
            default:
                return 0;
        }
    }

    private double getGoalKeeperPosition(String direction) {
        switch (direction) {
            case "left":
                return -200;
            case "right":
                return 200;
            default:
                return 0;
        }
    }

    private String getDirectionFromPosition(double position) {
        if (position < -50) return "left";
        if (position > 50) return "right";
        return "center";
    }

    private boolean isGoal(String shotDirection, String keeperDirection) {
        return !shotDirection.equals(keeperDirection);
    }

    private String randomDirection() {
        int choice = random.nextInt(3);
        return choice == 0 ? "left" : choice == 1 ? "center" : "right";
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 16px; -fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
    }
}
