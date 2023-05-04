package com.fundo.requests;

public class DepositRequest {
    public String accountId;
    public double usdAmount;

    public DepositRequest(String accountId, double usdAmount) {
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }
}
