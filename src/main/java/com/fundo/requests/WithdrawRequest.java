package com.fundo.requests;

public class WithdrawRequest {
    public String accountId;
    public double usdAmount;

    public WithdrawRequest(String accountId, double usdAmount) {
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }
}
