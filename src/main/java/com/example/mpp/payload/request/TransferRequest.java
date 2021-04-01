package com.example.mpp.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class TransferRequest {

    @NotBlank
    private double amount;
    @NotBlank
    private int toAccount;
    @NotBlank
    private int fromAccount;

    public double getAmount() {
        return amount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public int getFromAccount() {
        return fromAccount;
    }
}
