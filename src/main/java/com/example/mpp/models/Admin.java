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

//    public List<Customer> getBranchCustomers()
//    {
//        List<Customer> customerList=branch.getCustomers();
//        return  customerList;
//    }
//
//    public List<Teller> getBranchTellers(int branchId)
//    {
//        List<Teller> tellerList=branch.getTellers();
//        return  tellerList;
//    }

    public double getTotalDeposit()
    {   double totalDeposits = 0.0;
        return totalDeposits;
    }

    public double getTotalWithdrawal()
    {
        double totalWithdrawal=0.0;
        return totalWithdrawal;
    }

    public List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate)
    {
        List<Transaction>  transactionList=new ArrayList<>();
        List<Customer> customers;

        return  transactionList;
    }
}
