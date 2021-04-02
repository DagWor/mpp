package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "branch")
public class Branch {
    @Id
    private String id;
//    private String branchName;
//    private Address address;
//    private User user;


    private int depositAmount = 98987;
    private String branchName;
    private int branchId;
    private List<Customer> customers = new ArrayList<>();
    private List<Teller> tellers = new ArrayList<>();

    private User admin;

    public Branch(User admin){
        this.admin = admin;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void addCustomerToBranch(Customer user){
        customers.add(user);
    }


    public void addTellerToBranch(Teller user){
        tellers.add(user);
    }

    public List<Customer> getBranchCustomers(){
        return customers;
    }

    public void setBranchTellers(List<Teller> tellers){
        this.tellers = tellers;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Teller> getTellers() {
        return tellers;
    }

    public User getAdmin() {
        return admin;
    }


    public User getBranchAdmin(){
        return admin;
    }

    public void setAdmin(User user){
        this.admin = user;
    }

    public String getId() {
        return id;
    }

    public int getDepositAmuont() {
        return depositAmount;
    }
}
