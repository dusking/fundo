package com.fundo.models;

import com.fundo.exception.InsufficientFundsException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Hashtable;

public class Account {
    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String ownerId;

    private double usdAmount;
    private Hashtable<String, Double> holding  = new Hashtable<String, Double>();

    public Account() {
    }

    public Account(String ownerId) {
        this.ownerId = ownerId;
        this.usdAmount = 0;
    }

    public Boolean deposit(final double usdAmount) {
        this.usdAmount += usdAmount;
        return true;
    }

    public void withdraw(final double usdAmount) throws InsufficientFundsException {
        if (usdAmount > this.usdAmount) {
            throw new InsufficientFundsException("Failed to withdraw");
        }
        this.usdAmount -= usdAmount;
    }

    public String getId() {
        return this.id;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public double getUsdAmount() {
        return this.usdAmount;
    }

    public boolean sufficientFund(final Integer usdAmount) {
        return usdAmount >= this.usdAmount;
    }

    public Hashtable<String, Double> getHolding() {
        return this.holding;
    }

    public void buy(final String symbol, double stockAmount, double usdAmount) throws InsufficientFundsException {
        if (this.usdAmount < usdAmount) {
            throw new InsufficientFundsException("Failed to buy " + symbol);
        }
        double currentAmount = holding.getOrDefault(symbol, 0.0);
        holding.put(symbol, currentAmount + stockAmount);
        this.usdAmount -= usdAmount;
    }

    public void sell(final String symbol, double stockAmount, double usdAmount) throws InsufficientFundsException {
        double currentAmount = holding.getOrDefault(symbol, 0.0);
        if (currentAmount < stockAmount) {
            throw new InsufficientFundsException("Failed to sell " + symbol);
        }
        holding.put(symbol, currentAmount - stockAmount);
        this.usdAmount += usdAmount;
    }
}
