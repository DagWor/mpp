package com.example.mpp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Document(collection="Transaction")
public class Transaction {

    @Id
    private String transactionId;
    private LocalDate transactionDate;
    @NotBlank
    private double amount;
    private int toAccount;
    private int fromAccount;
    private int branchId;

    private AccountInfo accountInfo;

      private TransactionType type;
      public Transaction(){

      }

    public Transaction( LocalDate transactionDate, double amount, int toAccount, int fromAccount, int branchId,TransactionType type) {

        this.transactionDate = transactionDate;
        this.amount = amount;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.branchId = branchId;
        this.type=type;
    }
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    public String getTransactionId() {
        return transactionId;
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
