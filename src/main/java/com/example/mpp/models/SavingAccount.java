package com.example.mpp.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


public class SavingAccount  extends AccountInfo implements Account {
    public SavingAccount(int accountNumber, double balance, String type, LocalDate currentDate, Customer customer) {
        super(accountNumber, balance, type, currentDate, customer);
    }

    @Override
    public void getInterst() {

    }

    @Override
    public AccountType getAccountType() {
        return null;
    }

    @Override
    public double getAccount() {
        return 0;
    }
}