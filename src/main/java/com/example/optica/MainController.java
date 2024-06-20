package com.example.optica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    protected void openGlassesCatalog() {
        katalog catalog = new katalog();
        catalog.start(new Stage());
    }

    @FXML
    protected void openCatalog() {
        katalog catalog = new katalog();
        catalog.start(new Stage());
    }
    @FXML
    protected void openAccessoriesCatalog() {
        Accessories accessories = new Accessories();
        accessories.start(new Stage());
    }
    @FXML
    protected void openAppointmentBooking() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("appointment-booking.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Appointment Booking");
            double width = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() * 0.3;
            double height = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight() * 0.8;
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openCart(ActionEvent event) throws IOException {
        CartPage cartPage = new CartPage();
        cartPage.show();
    }
    private void openCatalog(String title, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}