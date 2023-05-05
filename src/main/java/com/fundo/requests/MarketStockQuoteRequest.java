package com.fundo.requests;

public class MarketStockQuoteRequest {
    public double price;

    public MarketStockQuoteRequest() {

    }

    public MarketStockQuoteRequest(String price) {
        this.price = Double.parseDouble(price);
    }
}
