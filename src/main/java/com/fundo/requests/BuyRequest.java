package com.fundo.requests;

public class BuyRequest {
    public String accountId;
    public String symbol;
    public double usdAmount;

    public BuyRequest(String accountId, String symbol, double usdAmount) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.usdAmount = usdAmount;
    }
}
