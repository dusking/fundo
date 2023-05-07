package com.fundo.requests;

import jakarta.validation.constraints.*;


public class DepositRequest {

    @NotEmpty(message = "accountId cannot be null")
    public String accountId;

    @Min(value = 1, message = "usdAmount should not be less than 1")
    @Max(value = 1000, message = "usdAmount should not be greater than 1000")
    public double usdAmount;

    public DepositRequest() {}

    public DepositRequest(String accountId, double usdAmount) {
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }
}
