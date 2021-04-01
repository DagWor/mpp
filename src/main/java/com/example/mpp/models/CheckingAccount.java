package com.example.mpp.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


public class CheckingAccount extends AccountInfo implements Account{

//    private int  interstRate;
    //private AccountInfo accountInfo;

    public CheckingAccount(int accountNumber, double balance,String type ,LocalDate currentDate,int customerId) {
         super(accountNumber, balance,type, currentDate, customerId);

        //accountInfo=new AccountInfo(accountNumber,balance,type,currentDate,customerId );
    }


    @Override
    public double getInterst() {

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
