package com.example.optica;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (dbHandler.loginUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
            openMainPage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void switchToRegisterScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent registerRoot = loader.load();

            // Увеличиваем размеры окна
            Stage stage = new Stage();
            Scene scene = new Scene(registerRoot, 400, 400); // Новые размеры
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();

            // Закрываем окно входа при открытии окна регистрации
            usernameField.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainPage() {
        // Открывает вашу главную страницу
        MainApplication mainApp = new MainApplication();
        try {
            mainApp.start(new Stage());
            // Закрываем окно входа после успешной авторизации
            usernameField.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


