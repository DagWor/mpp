package com.example.mpp.resources;

import org.springframework.data.annotation.Id;

public class CurrentAccountNumber {
    @Id
    private String id;
    private int currentAccountNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentAccountNumber() {
        return currentAccountNumber;
    }

    public void setCurrentAccountNumber(int currentAccountNumber) {
        this.currentAccountNumber = currentAccountNumber;
    }
}
