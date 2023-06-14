package com.example.guessnumber;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class numberGuessG extends Application {

    private int maxAttempts = 3;

    private int attempts = 0;
    private int randomNumber;

    private Label attemptsLabel;
    private Label resultLabel;
    private TextField guessField;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Number Guessing Game");

        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome to Number Guessing Game");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label instructionsLabel = new Label("Guess a number between 1 and 100:");
        instructionsLabel.setStyle("-fx-font-size: 14px;");

        guessField = new TextField();
        guessField.setPromptText("Enter your guess");
        guessField.setPrefWidth(200);

        Button guessButton = new Button("Guess");
        guessButton.setOnAction(e -> makeGuess());

        attemptsLabel = new Label("Attempts: 0/" + maxAttempts);
        attemptsLabel.setStyle("-fx-font-size: 14px;");

        resultLabel = new Label("");
        resultLabel.setStyle("-fx-font-size: 14px;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> resetGame());
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());
        buttonBox.getChildren().addAll(playAgainButton, quitButton);

        mainBox.getChildren().addAll(
                titleLabel,
                instructionsLabel,
                guessField,
                guessButton,
                attemptsLabel,
                resultLabel,
                buttonBox
        );

        randomNumber = generateRandomNumber();

        primaryStage.setScene(new Scene(mainBox, 550, 550));
        primaryStage.show();
    }

    private void makeGuess() {
        int guessedNumber;
        try {
            guessedNumber = Integer.parseInt(guessField.getText());
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid input. Please enter a number.");
            return;
        }

        attempts++;

        if (guessedNumber == randomNumber) {
            showResultMessage("WOW!! You guessed the right number. Congratulations!");
        } else if (guessedNumber < randomNumber && attempts != maxAttempts) {
            showResultMessage("Number is greater than the guessed number.");
        } else if (guessedNumber > randomNumber && attempts != maxAttempts) {
            showResultMessage("Number is smaller than the guessed number.");
        }

        attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);

        if (attempts == maxAttempts) {

            showResultMessage("Better luck next time! The correct number was: " + randomNumber);

            disableInput();

        }
    }

    private void showResultMessage(String message) {
        resultLabel.setText(message);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void disableInput() {
        guessField.setDisable(true);
    }

    private void resetGame() {
        attempts = 0;
        attemptsLabel.setText("Attempts: 0/" + maxAttempts);
        resultLabel.setText("");
        guessField.setDisable(false);
        guessField.clear();
        randomNumber = generateRandomNumber();
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}

