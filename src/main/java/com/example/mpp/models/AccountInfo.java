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



    private double balance;

    private String type;
    private LocalDate currentDate;
//    private Customer customer;
    private List<Transaction> transaction=new ArrayList<>();
    private String branchName ;
    private String customerId;

    public AccountInfo(){

    }

    public AccountInfo(int accountNumber, double balance, String type, LocalDate currentDate,String customerId) {
        this.transaction = new ArrayList<>();
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.currentDate = currentDate;
        this.customerId=customerId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
