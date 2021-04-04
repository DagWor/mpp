package com.example.mpp.services;

import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.Transaction;
import com.example.mpp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class AccountServices {
    @Autowired
    private AccountRepository accountRepository;

    public String addTransactionToAccount(int accountNumber, Transaction transaction){
        Optional<AccountInfo> accountInfo1=accountRepository.
                findAccountInfoByAccountNumber(accountNumber);
        if (accountInfo1.isPresent()){
            List<Transaction> transactionList=accountInfo1.get().getTransaction();
            transactionList.add(transaction);
            accountInfo1.get().setTransaction(transactionList);
            return "success";
        }
        return "not found";

    }
}
