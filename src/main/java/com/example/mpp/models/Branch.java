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
    private String branchName;
    private Address address;
    private List<User> user;

    public Branch(String branchName, Address address, List<User> user) {
        this.branchName = branchName;
        this.address = address;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    //    private int depositAmount = 98987;
//    private String branchName;
//
//    public String getBranchName() {
//        return branchName;
//    }
//
//    public void setBranchName(String branchName) {
//        this.branchName = branchName;
//    }
//
//    public int getBranchId() {
//        return branchId;
//    }
//
//    public void setBranchId(int branchId) {
//        this.branchId = branchId;
//    }
//
//    private int branchId;
//    private List<User> customers = new ArrayList<>();
//    private List<User> tellers = new ArrayList<>();
//
//    private User admin;
//
//    public Branch(User admin){
//        this.admin = admin;
//        tellers.add(admin);
//        customers.add(admin);
//    }
//    public void addCustomerToBranch(User user){
//        customers.add(user);
//    }
//
//
//    public void addTellerToBranch(User user){
//        tellers.add(user);
//    }
//
//    public List<User> getBranchCustomers(){
//        return customers;
//    }
//
//    public void setBranchTellers(List<User> tellers){
//        this.tellers = tellers;
//    }
//
//    public int getDepositAmount() {
//        return depositAmount;
//    }
//
//    public void setDepositAmount(int depositAmount) {
//        this.depositAmount = depositAmount;
//    }
//
//    public List<User> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<User> customers) {
//        this.customers = customers;
//    }
//
//    public List<User> getTellers() {
//        return tellers;
//    }
//
//    public User getAdmin() {
//        return admin;
//    }
//
//    public List<User> getBranchTellers(){
//        return tellers;
//    }
//    public User getBranchAdmin(){
//        return admin;
//    }
//
//    public void setAdmin(User user){
//        this.admin = user;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public int getDepositAmuont() {
//        return depositAmount;
//    }
}
