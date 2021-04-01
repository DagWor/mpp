package com.example.mpp.controllers;

import com.example.mpp.models.Transaction;
import com.example.mpp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void transfer(@RequestBody Transaction transaction){
       // transactionRepository.save(transaction);

    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void makeWithdrawal(double amount){

    }




}
