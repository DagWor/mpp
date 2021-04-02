package com.example.mpp.models;

import com.example.mpp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class Teller extends User {

    private String firstName;
    private String lastName;
    private String branchId;
    private User user;

    public  Teller(User user,String firstName,String lastName) {
        super(user.getUsername(), user.getEmail(), user.getPassword());
        this.firstName=firstName;
        this.lastName=lastName;
    }

    @Autowired
    private AccountRepository accountRepository;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBranchId() {
        return branchId;
    }

    public  boolean makeDeposit(int accountNumber,double amount)
    {
        boolean deposited=false;
        if(!accountRepository.existsAccountInfoByAccountNumber(accountNumber))
        {
//            Optional<AccountInfo> account=accountRepository.findAccountInfoByAccountNumber(accountNumber);
//            double currentBalance= account.getBalance()+ amount;
//            setBalance(currentBalance) ;
//            accountRepository.
        }

        return  deposited;
    }

    public boolean makeWithdrawal(int accountNumber, double amount)
    {
        boolean withdrawed=false;


        return  withdrawed;
    }

    public  boolean makeTransfer(Account accountFrom , Account accountTo,double amount)
    {
       boolean transferSucced=false;

       return  transferSucced;
    }
}

