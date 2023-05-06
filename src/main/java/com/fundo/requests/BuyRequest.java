package com.fundo.requests;

import jakarta.validation.constraints.*;


public class BuyRequest {
    @NotEmpty(message = "accountId cannot be null")
    public String accountId;

    @Size(min = 2, max = 10, message=  "Symbol must be between 2 and 10 characters")
    public String symbol;

    @Min(value = 1, message = "usdAmount should not be less than 1")
    @Max(value = 1000, message = "usdAmount should not be greater than 1000")
    public double usdAmount;

    public BuyRequest(String accountId, String symbol, double usdAmount) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.usdAmount = usdAmount;
    }
}
