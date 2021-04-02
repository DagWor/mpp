package com.example.mpp.payload.request;

import com.example.mpp.models.Customer;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class CreateAccountRequest {
    @Id
    private String id;


    private String username;

    private double balance;

    private String type;
    private LocalDate currentDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
}
