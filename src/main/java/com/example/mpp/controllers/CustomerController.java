package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.payload.request.TransferRequest;
import com.example.mpp.repository.AccountRepository;
import com.example.mpp.repository.CustomerRepository;
import com.example.mpp.repository.TransactionRepository;
import com.example.mpp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void transfer(@RequestBody TransferRequest transferRequest){
        Transaction transaction = new Transaction();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        Customer customer = customerRepository.findCustomerByUser(user);

        transaction.setCustomer(customer);
        transaction.setAmount(transferRequest.getAmount());
        transaction.setFromAccount(transferRequest.getFromAccount());
        transaction.setToAccount(transferRequest.getToAccount());
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
