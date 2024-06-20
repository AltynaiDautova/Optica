package com.example.optica;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentPage extends Stage {

    private TextField cardNumberField;
    private TextField expiryDateField;
    private TextField cvvField;

    public PaymentPage() {
        VBox paymentLayout = createPaymentLayout();

        // Создание сцены и установка ее для этого окна
        Scene scene = new Scene(paymentLayout, 400, 300);
        setScene(scene);

        // Установка заголовка окна
        setTitle("Payment");

        // Установка модального режима для окна оплаты (блокирование взаимодействия с основным окном)
        initModality(Modality.APPLICATION_MODAL);
    }

    private VBox createPaymentLayout() {
        VBox layout = new VBox();
        layout.setPadding(new Insets(20));
        layout.setSpacing(10);

        Label titleLabel = new Label("Payment");
        titleLabel.setStyle("-fx-font-size: 24px;");

        GridPane paymentForm = createPaymentForm();

        Button completePaymentButton = new Button("Complete Payment");
        completePaymentButton.setOnAction(event -> completePayment());

        layout.getChildren().addAll(titleLabel, paymentForm, completePaymentButton);

        return layout;
    }

    private GridPane createPaymentForm() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label cardNumberLabel = new Label("Card Number:");
        cardNumberField = new TextField();
        Label expiryDateLabel = new Label("Expiry Date:");
        expiryDateField = new TextField();
        Label cvvLabel = new Label("CVV:");
        cvvField = new TextField();

        gridPane.add(cardNumberLabel, 0, 0);
        gridPane.add(cardNumberField, 1, 0);
        gridPane.add(expiryDateLabel, 0, 1);
        gridPane.add(expiryDateField, 1, 1);
        gridPane.add(cvvLabel, 0, 2);
        gridPane.add(cvvField, 1, 2);

        return gridPane;
    }

    private void completePayment() {
        if (validateInput()) {
            Label successLabel = new Label("Payment Successfully Completed!");

            // Очистка корзины после успешной оплаты
            CartPage.clearCart();

            // Обновление интерфейса
            VBox paymentLayout = (VBox) getScene().getRoot();
            paymentLayout.getChildren().clear();
            paymentLayout.getChildren().add(successLabel);
        }
    }

    private boolean validateInput() {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            showError("All fields must be filled");
            return false;
        }

        if (!cardNumber.matches("\\d{16}")) {
            showError("Card number must be 16 digits");
            return false;
        }

        if (!expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            showError("Expiry date must be in MM/YY format");
            return false;
        }

        if (!cvv.matches("\\d{3}")) {
            showError("CVV must be 3 digits");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: red;");

        VBox paymentLayout = (VBox) getScene().getRoot();
        if (paymentLayout.getChildren().size() > 3) {
            paymentLayout.getChildren().remove(3);
        }
        paymentLayout.getChildren().add(errorLabel);
    }
}
