package com.example.optica;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    protected void openGlassesCatalog() {
        openCatalog("Glasses Catalog", "glasses-catalog.fxml");
    }

    @FXML
    protected void openAccessoriesCatalog() {
        openCatalog("Accessories Catalog", "accessories-catalog.fxml");
    }

    @FXML
    protected void openAppointmentBooking() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("appointment-booking.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Appointment Booking");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCatalog(String title, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
