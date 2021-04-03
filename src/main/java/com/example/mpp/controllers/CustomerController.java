package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.payload.request.TransferRequest;
import com.example.mpp.repository.*;
import com.example.mpp.repository.resources.UserWithIDRepository;
import com.example.mpp.resources.UserWithID;
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

    @Autowired
    private UserWithIDRepository userWithIDRepository;


    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest transferRequest){
        Transaction transaction = new Transaction();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        Customer customer = customerRepository.findCustomerByUser(user);
        AccountInfo accountInfo = accountRepository.findAccountInfoByCustomer(customer);
        if(accountInfo.getAccountNumber() == transferRequest.getToAccount()){
            transaction.setCustomer(customer);
            transaction.setAmount(transferRequest.getAmount());
            transaction.setFromAccount(transferRequest.getFromAccount());
            transaction.setToAccount(transferRequest.getToAccount());

            Optional<AccountInfo> accountInfo1 = accountRepository.findAccountInfoByAccountNumber(transferRequest.getToAccount());

            if(accountInfo1.isPresent()){
                AccountInfo accountInfo2 = accountInfo1.get();
                transactionRepository.save(transaction);
                List<Transaction> transactions = accountInfo.getTransactionList();
                transactions.add(transaction);
                accountInfo.setTransactionList(transactions);
                accountRepository.save(accountInfo);

                List<Transaction> transactions1 = accountInfo2.getTransactionList();
                transactions1.add(transaction);
                accountInfo2.setTransactionList(transactions1);
                accountRepository.save(accountInfo2);

                return new ResponseEntity<>(transaction, HttpStatus.OK);

            } else {
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }




        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Customer>> accounts(){

        List<Customer> users = customerRepository.findAll();

//        List<Customer> c = customerRepository.findAll();

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserWithID userWithID = userWithIDRepository.findUserWithIDByUsername("dagy");


//        User user = userRepository.findUserByUsername(auth.getName());
//        Customer customer = customerRepository.findCustomerByUser(user);
//        List<AccountInfo> accounts = accountRepository.findAllByCustomer(customer);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable("accountNumber") int accountNumber){
        Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(accountNumber);
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountInfo(accountInfo.get());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

//    @GetMapping("/transactions")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<List<Transaction>> transactions(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findUserByUsername(auth.getName());
//
////        Customer customer = customerRepositor.findCustomerByUserUsername(auth.getName());
////        List<Transaction> transactions = transactionRepository.findTransactionsByCustomer(customer);
//
//
//        return new ResponseEntity<>(transactions, HttpStatus.OK);
//    }


}
