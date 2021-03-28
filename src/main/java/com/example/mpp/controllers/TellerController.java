package com.example.mpp.controllers;

import com.example.mpp.models.Branch;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teller")
public class TellerController {

    @GetMapping("/deposit")
    @PreAuthorize("hasRole('TELLER')")
    public void makeDeposit(double amount){

    }

    @GetMapping("/withdraw")
    @PreAuthorize("hasRole('TELLER')")
    public void makeWithdrawal(double amount){

    }

    @GetMapping("/transfer")
    @PreAuthorize("hasRole('TELLER')")
    public double totalDeposit(){
        Branch b = new Branch();
        return b.getDepositAmuont();
    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('TELLER')")
    public double viewBalance(){
        return 0.0;
    }

    @GetMapping("/create-transfer")
    @PreAuthorize("hasRole('TELLER')")
    public void createTransfer(){

    }
}
