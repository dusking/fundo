package com.fundo.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class DepositRequest {

    @NotEmpty(message = "The accountId is required.")
    @NotNull(message = "The accountId is required.")
    public String accountId;

    @Positive()
    public double usdAmount;

    public DepositRequest(String accountId, double usdAmount) {
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }
}
