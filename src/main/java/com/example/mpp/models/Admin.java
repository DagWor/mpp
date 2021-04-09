package com.example.mpp.models;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{

    private String firstName;
    private String lastName;
    private Branch branch;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Branch getBranch() {
        return branch;
    }

    public  Admin(String username, String email, String password, String firstName, String lastName)
    {
        super(username,email,password);
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public double getTotalDeposit() {
        return 0.0;
    }

    public double getTotalWithdrawal() {
        return 0.0;
    }

    public List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>();
    }
}
