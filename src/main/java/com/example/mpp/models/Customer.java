package com.example.mpp.models;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Branch branch;

    public Branch getBranch() {
        return branch;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }


    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer(String username,String email,String password,String firstName,String lastName)
    {
        super(username,email,password);
        this.firstName=firstName;
        this.lastName=lastName;
    }


}
