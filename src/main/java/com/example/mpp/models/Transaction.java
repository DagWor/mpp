package com.example.mpp.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Transaction {

    @Id
    private int transactionId;
    private LocalDate transactionDate;
    private double amount;
    private int toAccount;
    private int fromAccount;
    private int branchId;

    public Transaction(int transactionId, LocalDate transactionDate, double amount, int toAccount, int fromAccount, int branchId) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.branchId = branchId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
