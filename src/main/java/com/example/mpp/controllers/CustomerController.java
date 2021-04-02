package com.example.mpp.controllers;

import com.example.mpp.models.Account;
import com.example.mpp.models.AccountInfo;
import com.example.mpp.models.Transaction;
import com.example.mpp.models.User;
import com.example.mpp.repository.AccountRepository;
import com.example.mpp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void transfer(@RequestBody Transaction transaction){
       // transactionRepository.save(transaction);

    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void makeWithdrawal(double amount){

    }

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<AccountInfo>> accounts(){
        List<AccountInfo> accounts = accountRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable("accountNumber") int accountNumber){
        Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(accountNumber);
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountInfo(accountInfo.get());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


}
