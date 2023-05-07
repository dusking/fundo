package com.fundo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashMap;
import java.util.Map;

public class Account {
    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String ownerId;

    private double usdAmount;

    private Map<String, Double> holding  = new HashMap<>();

    public Account() {
    }

    public Account(String ownerId) {
        this.ownerId = ownerId;
        this.usdAmount = 0;
    }

    public Account(String ownerId, String id) {
        this.id = id;
        this.ownerId = ownerId;
        this.usdAmount = 0;
    }

    public String getId() {
        return this.id;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Map<String, Double> getHolding() { return this.holding; }

    public double getUsdAmount() {
        return this.usdAmount;
    }

    public void addUsdAmount(double usdAmount) {
        this.usdAmount += usdAmount;
    }

    public Double getHolding(String symbol) { return this.holding.getOrDefault(symbol, 0.0); }

    public void addHolding(final String symbol, double stockAmount) {
        double currentAmount = holding.getOrDefault(symbol, 0.0);
        holding.put(symbol, currentAmount + stockAmount);
    }

}
