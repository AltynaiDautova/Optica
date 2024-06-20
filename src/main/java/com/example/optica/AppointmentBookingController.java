package com.example.optica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
public class AppointmentBookingController {
    @FXML
    private TextField fullNameField;
    @FXML
    private DatePicker dateOfAppointmentPicker;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ChoiceBox<String> specialistChoiceBox;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private ChoiceBox<String> timeChoiceBox;
    @FXML
    private Button submitButton;
    @FXML
    private TableView<Service> serviceTable;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<Service> services = FXCollections.observableArrayList();

    @FXML
    protected void handleSubmitButtonAction() {
        String fullName = fullNameField.getText();
        LocalDate dateOfBirth = dateOfAppointmentPicker.getValue();
        String phoneNumber = phoneNumberField.getText();
        String selectedSpecialist = specialistChoiceBox.getValue();
        String selectedTime = timeChoiceBox.getValue();

        if (fullName.isEmpty() || dateOfBirth == null || phoneNumber.isEmpty() || selectedSpecialist == null || selectedTime == null) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please fill in all fields.");
            return;
        }

        Appointment appointment = new Appointment(fullName, dateOfBirth, phoneNumber, selectedSpecialist, selectedTime);
        appointments.add(appointment);
        appointmentTable.getItems().add(appointment);

        showAlert(Alert.AlertType.INFORMATION, "Success", null, "Form submitted successfully!\nSelected specialist: " + selectedSpecialist + "\nSelected time: " + selectedTime);

        clearFormFields();
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearFormFields() {
        fullNameField.clear();
        dateOfAppointmentPicker.setValue(null);
        phoneNumberField.clear();
        specialistChoiceBox.getSelectionModel().clearSelection();
        timeChoiceBox.getSelectionModel().clearSelection();
    }
    private void initSpecialistChoiceBox() {
        ObservableList<String> specialists = FXCollections.observableArrayList("Specialist 1", "Specialist 2", "Specialist 3");
        specialistChoiceBox.setItems(specialists);
    }
    private void initTimeChoiceBox() {
        ObservableList<String> times = FXCollections.observableArrayList("08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45");
        timeChoiceBox.setItems(times);
    }
    private void initServiceList() {
        services.add(new Service(1, "Eye Exam", "$50", "ID"));
        services.add(new Service(2, "Glasses", "$100", "Prescription"));
        services.add(new Service(3, "Contact Lenses", "$150", "Prescription"));
    }
    @FXML
    public void initialize() {
        initSpecialistChoiceBox();
        initTimeChoiceBox();
        initServiceList();
        initAppointmentTable();
        initServiceTable();
    }
    private void initAppointmentTable() {
        appointmentTable.getColumns().clear();

        TableColumn<Appointment, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Appointment, LocalDate> dateOfAppointmentColumn = new TableColumn<>("Date of Appointment");
        dateOfAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfAppointment"));

        TableColumn<Appointment, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Appointment, String> specialistColumn = new TableColumn<>("Specialist");
        specialistColumn.setCellValueFactory(new PropertyValueFactory<>("specialist"));

        TableColumn<Appointment, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        appointmentTable.getColumns().addAll(fullNameColumn, dateOfAppointmentColumn, phoneNumberColumn, specialistColumn, timeColumn);
    }
    private void initServiceTable() {
        serviceTable.getColumns().clear();

        TableColumn<Service, Integer> serviceNumberColumn = new TableColumn<>("Service Number");
        serviceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serviceNumber"));

        TableColumn<Service, String> serviceNameColumn = new TableColumn<>("Service Name");
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Service, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<Service, String> requiredDocumentsColumn = new TableColumn<>("Required Documents");
        requiredDocumentsColumn.setCellValueFactory(new PropertyValueFactory<>("requiredDocuments"));

        serviceTable.getColumns().addAll(serviceNumberColumn, serviceNameColumn, costColumn, requiredDocumentsColumn);
        serviceTable.setItems(services);
    }
}
