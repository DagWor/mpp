package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Document(collection = "Account")
public class AccountInfo {

    @Id
    private String id;

    private int accountNumber;
 //private  Customer customerNUmber;

    private int customerId;

    private double balance;

    private String type;
    private LocalDate currentDate;
    private Customer customer;



    public AccountInfo(int accountNumber, double balance, String type , LocalDate currentDate,Customer customer) {

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.currentDate = currentDate;
        this.customer=customer;
    }
    public AccountInfo(int accountNumber, double balance, String type , LocalDate currentDate) {

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.currentDate = currentDate;

    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getId() {
        return id;
    }



    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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
