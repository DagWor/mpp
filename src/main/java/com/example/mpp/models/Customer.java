package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "customer")
public class Customer {

    @Id
    private String id;
    private int customerId;

    private User user;
    private List<AccountInfo > account;




    public Customer(  User user) {

        this.user = user;

    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AccountInfo> getAccount() {
        return account;
    }

    public void setAccount(List<AccountInfo> account) {
        this.account = account;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
