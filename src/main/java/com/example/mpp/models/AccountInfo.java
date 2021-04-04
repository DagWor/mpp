package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<Transaction> transaction;
    private String branchName ;

    public AccountInfo(){

    }

    public AccountInfo(int accountNumber, double balance, String type, LocalDate currentDate, Customer customer) {
        this.transaction = new ArrayList<>();
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


    }
    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        if(this.transaction == null){
            this.transaction = new ArrayList<>();
        }
        this.transaction = transaction;
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
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


}
