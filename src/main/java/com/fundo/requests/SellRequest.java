package com.fundo.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class SellRequest {
    @NotEmpty(message = "accountId cannot be null")
    public String accountId;

    @Size(min = 2, max = 10, message=  "Symbol must be between 2 and 10 characters")
    public String symbol;

    @Min(value = 1, message = "stockAmount should not be less than 1")
    @Max(value = 1000, message = "stockAmount should not be greater than 1000")
    public double stockAmount;

    public SellRequest(String accountId, String symbol, double stockAmount) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.stockAmount = stockAmount;
    }
}
