package com.example.mpp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void makeDeposit(double amount){

    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void makeWithdrawal(double amount){

    }
}
