package com.example.optica;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class AppointmentBookingController {

    @FXML
    private TextField fullNameField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ChoiceBox<String> specialistChoiceBox; // Добавляем поле для выбора специалиста

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction() {
        String fullName = fullNameField.getText();
        String dateOfBirth = dateOfBirthPicker.getValue().toString();
        String phoneNumber = phoneNumberField.getText();
        String selectedSpecialist = specialistChoiceBox.getValue(); // Получаем выбранного специалиста

        // Здесь вы можете добавить логику для отправки данных куда-либо
        // Например, сохранение в базе данных или отправка на сервер

        // После успешной отправки формы выводим сообщение об успехе
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Успех");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Форма успешно отправлена!\nВыбранный специалист: " + selectedSpecialist);
        successAlert.showAndWait();

        // Очищаем поля формы после отправки
        fullNameField.clear();
        dateOfBirthPicker.getEditor().clear();
        phoneNumberField.clear();
        specialistChoiceBox.getSelectionModel().clearSelection(); // Очищаем выбор специалиста
    }
}
