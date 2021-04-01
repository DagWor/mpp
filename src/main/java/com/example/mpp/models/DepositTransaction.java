package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Document(collection = "DepositTransaction")
public class DepositTransaction {
    @Id
    private String transactionId;
    private LocalDate transactionDate;
    @NotBlank
    private double amount;
    private int toAccount;
    private int branchId;
    private TransactionType type;

    public DepositTransaction( LocalDate transactionDate, @NotBlank double amount, int toAccount, int branchId) {

        this.transactionDate = transactionDate;
        this.amount = amount;
        this.toAccount = toAccount;
        this.branchId = branchId;

    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
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

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
