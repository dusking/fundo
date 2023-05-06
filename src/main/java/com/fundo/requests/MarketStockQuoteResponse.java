package com.fundo.requests;


public class MarketStockQuoteResponse {
    public double price;

    public MarketStockQuoteResponse() {
    }

    public MarketStockQuoteResponse(String price) {
        this.price = Double.parseDouble(price);
    }
}
