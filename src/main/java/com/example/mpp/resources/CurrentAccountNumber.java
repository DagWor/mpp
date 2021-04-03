package com.example.mpp.resources;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resources")
public class CurrentAccountNumber {
    @Id
    private String id;
    private int currentAccountNumber;

    public CurrentAccountNumber(int currentAccountNumber) {
        this.currentAccountNumber = currentAccountNumber;
    }

    public String getId() {
        return id;
    }



    public int getCurrentAccountNumber() {
        return currentAccountNumber;
    }

    public void setCurrentAccountNumber(int currentAccountNumber) {
        this.currentAccountNumber = currentAccountNumber;
    }
}
