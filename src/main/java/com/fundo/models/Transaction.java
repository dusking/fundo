package com.fundo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fundo.enums.TransactionStatus;
import com.fundo.enums.TransactionType;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class Transaction {
    @Id
    private String id;
    private Date createdUtc;
    private String accountId;
    private TransactionType action;
    private TransactionStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double usdAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double stockAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String symbol;

    public Transaction() {
        this.createdUtc = new Date();
        this.status = TransactionStatus.INIT;
        this.usdAmount = 0.0;
    }

    public String getId() {return this.id;}
    public Date getCreatedUtc() {return this.createdUtc;}
    public String getAccountId() {return this.accountId;}
    public TransactionType getAction() {return this.action;}
    public Double getUsdAmount() {return this.usdAmount;}
    public Double getStockAmount() {return this.stockAmount;}
    public String getSymbol() {return this.symbol;}
    public TransactionStatus getStatus() {return this.status;}

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
