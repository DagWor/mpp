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

import javax.validation.Valid;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Transaction> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        Transaction transaction = new Transaction();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        Customer customer = customerRepository.findCustomerByUser(user);
        AccountInfo accountInfo = accountRepository.findAccountInfoByCustomer(customer);
        if (accountInfo.getAccountNumber() == transferRequest.getToAccount()) {
            transaction.setAmount(transferRequest.getAmount());
            transaction.setFromAccount(transferRequest.getFromAccount());
            transaction.setToAccount(transferRequest.getToAccount());

            Optional<AccountInfo> accountInfo1 = accountRepository.findAccountInfoByAccountNumber(transferRequest.getToAccount());

            if (accountInfo1.isPresent()) {
                AccountInfo accountInfo2 = accountInfo1.get();
                transactionRepository.save(transaction);
                List<Transaction> transactions = accountInfo.getTransaction();
                transactions.add(transaction);
                accountInfo.setTransaction(transactions);
                accountRepository.save(accountInfo);

                List<Transaction> transactions1 = accountInfo2.getTransaction();
                transactions1.add(transaction);
                accountInfo2.setTransaction(transactions1);
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
    public ResponseEntity<List<AccountInfo>> accounts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());


        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer != null){
            List<AccountInfo> accounts = customer.getAccount();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*@GetMapping("/account/{accountNumber}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable("accountNumber") int accountNumber) {
        Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(accountNumber);
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountNumber(accountInfo.get().getAccountNumber());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }*/

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Transaction>> transactions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());

        Customer customer = customerRepository.findCustomerByUser(user);

        if(customer != null){
            List<AccountInfo> accounts = customer.getAccount();
            List<Transaction> transactions = new ArrayList<>();

            for (AccountInfo accountInfo : accounts){
                for (Transaction transaction : accountInfo.getTransaction()){
                    transactions.add(transaction);
                }
            }


            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
