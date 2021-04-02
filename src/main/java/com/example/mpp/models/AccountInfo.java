package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "Account")
public class AccountInfo {

    @Id
    private String id;

    private int accountNumber;
 //private  Customer customerNUmber;

    private int customerId;

    private double balance;

    private List<Transaction> transactions;

    private String type;
        private LocalDate currentDate;

    public AccountInfo( int accountNumber, double balance,String type ,LocalDate currentDate,int customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.currentDate = currentDate;
        this.customerId = customerId;
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

    public List<Transaction> getTransactionList() {
        return transactions;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactions = transactionList;
    }
}
