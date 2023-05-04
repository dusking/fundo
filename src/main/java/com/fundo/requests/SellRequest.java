package com.fundo.requests;

public class SellRequest {
    public String accountId;
    public String symbol;
    public double stockAmount;

    public SellRequest(String accountId, String symbol, double stockAmount) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.stockAmount = stockAmount;
    }
}
