package com.example.optica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Authorization extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml")); // Начать с экрана авторизации
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 200)); // Увеличенный размер окна
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
