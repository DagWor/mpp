package com.example.mpp.payload.request;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Component
public class DepositRequest {


    private double amount;
    @NotBlank
    private int accountNumber;




    public double getAmount() {
        return amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
