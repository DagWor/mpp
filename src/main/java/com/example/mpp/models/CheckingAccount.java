package com.example.mpp.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Document(collection = "Account")
public class CheckingAccount extends AccountInfo implements Account{

    private double interestRate = 0.05;

    public CheckingAccount(int accountNumber, double balance,String type ,LocalDate currentDate,String customerId) {
         super(accountNumber, balance,type, currentDate,customerId);

        //accountInfo=new AccountInfo(accountNumber,balance,type,currentDate,customerId );
    }

    @Override
    public void getInterst() {
        interestRate = interestRate / 365;
        //interest calculation based on specified interest rate

        double numberOfDays = Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), super.getCurrentDate()));
        super.setBalance(super.getBalance() + (interestRate * super.getBalance() * numberOfDays)); //account.user.getCurrentBalance(); //* (latest transaction date - now());
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
