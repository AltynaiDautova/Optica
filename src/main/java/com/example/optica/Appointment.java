package com.example.optica;
import java.time.LocalDate;
public class Appointment {
    private String fullName;
    private LocalDate dateOfAppointment;
    private String phoneNumber;
    private String specialist;
    private String time;
    public Appointment(String fullName, LocalDate dateOfAppointment, String phoneNumber, String specialist, String time) {
        this.fullName = fullName;
        this.dateOfAppointment = dateOfAppointment;
        this.phoneNumber = phoneNumber;
        this.specialist = specialist;
        this.time = time;
    }
    public String getFullName() {
        return fullName;
    }
    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getSpecialist() {
        return specialist;
    }
    public String getTime() {
        return time;
    }
}
