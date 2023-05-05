package com.fundo.models;

import com.fundo.enums.TransactionStatus;
import com.fundo.enums.TransactionType;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Hashtable;

public class Transaction {
    @Id
    private String id;
    private Date createdUtc;
    private String accountId;
    private TransactionType action;
    private Double usdAmount;
    private Double stockAmount;
    private String symbol;
    private TransactionStatus status;

    public Transaction() {
        this.createdUtc = new Date();
        this.status = TransactionStatus.INIT;
        this.usdAmount = 0.0;
    }

    public Hashtable<String, String> getData() {
        Hashtable<String, String> response  = new Hashtable<>();
        response.put("id", this.id);
        response.put("createdUtc", this.createdUtc.toString());
        response.put("status", this.status.toString());
        response.put("action", this.action.toString());
        response.put("accountId", this.accountId);
        if (this.action == TransactionType.DEPOSIT || this.action == TransactionType.WITHDRAW) {
            response.put("usdAmount", this.usdAmount.toString());
        } else if (this.action == TransactionType.BUY || this.action == TransactionType.SELL) {
            response.put("symbol", this.symbol);
            response.put("usdAmount", this.usdAmount.toString());
            response.put("stockAmount", this.stockAmount.toString());
        }
        return response;
    }

    public void setDeposit(String accountId, double usdAmount) {
        this.action = TransactionType.DEPOSIT;
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }

    public void setBuy(String accountId, String symbol, double usdAmount) {
        this.action = TransactionType.BUY;
        this.accountId = accountId;
        this.symbol = symbol;
        this.usdAmount = usdAmount;
    }

    public void setStockAmount(double stockAmount) {
        this.stockAmount = stockAmount;
    }

    public void setUsdAmount(double usdAmount) {
        this.usdAmount = usdAmount;
    }

    public void setSell(String accountId, String symbol, double stockAmount) {
        this.action = TransactionType.SELL;
        this.accountId = accountId;
        this.symbol = symbol;
        this.stockAmount = stockAmount;
    }

    public void setWithdraw(String accountId, double usdAmount) {
        this.action = TransactionType.WITHDRAW;
        this.accountId = accountId;
        this.usdAmount = usdAmount;
    }

    public void setSuccess() {
        this.status = TransactionStatus.SUCCESS;
    }

    public void setFailure() {
        this.status = TransactionStatus.FAILURE;
    }
}
