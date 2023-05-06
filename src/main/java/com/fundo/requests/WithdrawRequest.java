package com.fundo.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


public class WithdrawRequest {
    @NotEmpty(message = "accountId cannot be null")
    public String accountId;

    @Min(value = 1, message = "usdAmount should not be less than 1")
    @Max(value = 1000, message = "usdAmount should not be greater than 1000")
    public double usdAmount;

    public WithdrawRequest(String accountId, double usdAmount) {
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }
}
