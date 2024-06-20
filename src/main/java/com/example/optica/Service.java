package com.example.optica;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Service {
    private final SimpleIntegerProperty serviceNumber;
    private final SimpleStringProperty serviceName;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty requiredDocuments;

    public Service(int serviceNumber, String serviceName, String cost, String requiredDocuments) {
        this.serviceNumber = new SimpleIntegerProperty(serviceNumber);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.cost = new SimpleStringProperty(cost);
        this.requiredDocuments = new SimpleStringProperty(requiredDocuments);
    }

    public int getServiceNumber() {
        return serviceNumber.get();
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public String getCost() {
        return cost.get();
    }

    public String getRequiredDocuments() {
        return requiredDocuments.get();
    }
}
