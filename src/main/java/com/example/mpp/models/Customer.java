package com.example.mpp.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;
@Document(collection = "customer")
public class Customer  extends User{
    @Id
    private int id;
    private int customerId;
    private Address address;
    private User user;
    private List<Account > account;

    public Customer(  User user) {
        this.user = user;
    }

//    @Override
//    public String getId() {
//        return id;
//    }
//    @Override
//    public void setId(String id) {
//        this.id = id;
//    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Account> getAccount() {
        return account;
    }
    public void setAccount(List<Account> account) {
        this.account = account;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}