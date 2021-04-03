package com.example.mpp.controllers;

import com.example.mpp.models.*;
import com.example.mpp.repository.*;
import com.example.mpp.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    DepositRepository depositRepository;

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void transfer(@RequestBody Transaction transaction) {
        // transactionRepository.save(transaction);

    }

    @PostMapping("/withdrawal")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void withdrawal(double amount) {
        // transactionRepository.save(transaction);

    }


    @GetMapping("/viewBalance")
    @PreAuthorize("hasRole('Customer')")
    public double viewBalance(@PathVariable("id") int accountNumber) {
          double currentBalance = 0;
        try {

            Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByAccountNumber(accountNumber);
            AccountInfo accountInfo1=accountInfo.get();
            currentBalance=accountInfo1.getBalance();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentBalance;
    }

     @GetMapping("/viewAccounts/{id")
     @PreAuthorize("hasRole('Customer')")
     public ResponseEntity<List<AccountInfo>> viewAccounts(@PathVariable("id") int customerId)
     {
       try {
           List<AccountInfo> accountInfos = accountRepository.findAllByCustomerId(customerId);
           if(accountInfos.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           else return new ResponseEntity<>(accountInfos, HttpStatus.OK);

           // accountInfo = accountInfo1.getAccountNumbers();


       }catch (Exception e)
       {
           System.out.println(e.getMessage());
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
     }

     @GetMapping("/viewTransactions/{id}")
     @PreAuthorize("hasRole('Customer')")
     public ResponseEntity<List<Transaction>> viewTransactions(@PathVariable("id") int customerId)
     {
         List<Transaction> transactionList = new ArrayList<>();
         int accountNo;

         //get account number first

         try{
             Optional<AccountInfo> accountInfo = accountRepository.findAccountInfoByCustomerId(customerId);
             AccountInfo accountInfo1=accountInfo.get();
             accountNo=accountInfo1.getAccountNumber();

             Optional<Transaction> transaction= Optional.ofNullable(transactionRepository.findTransactionsByToAccount(accountNo));
             Optional<Transaction> transaction1= Optional.ofNullable(transactionRepository.findTransactionsByFromAccount(accountNo));


            return new ResponseEntity<>(transactionList, HttpStatus.OK);


         }catch(Exception e)
         {
             System.out.println(e.getMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }


     }


}
