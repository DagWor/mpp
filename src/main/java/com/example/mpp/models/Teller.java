package com.example.mpp.models;

import com.example.mpp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class Teller extends User {

    private String firstName;
    private String lastName;
    private String branchId;

    public  Teller(User user,String firstName,String lastName) {
        super(user.getUsername(), user.getEmail(), user.getPassword());
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBranchId() {
        return branchId;
    }

    public  boolean makeDeposit(int accountNumber,double amount) {
        return  false;
    }

    public boolean makeWithdrawal(int accountNumber, double amount) {
        return false;
    }

    public  boolean makeTransfer(Account accountFrom , Account accountTo,double amount) {
       return false;
    }
}

