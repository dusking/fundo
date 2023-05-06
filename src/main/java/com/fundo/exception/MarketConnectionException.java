package com.fundo.exception;


public class MarketConnectionException extends Exception {
    private String message;

    public MarketConnectionException() {
        super();
        this.message = "Invalid market request.";
    }

    public MarketConnectionException(String message) {
        super();
        this.message = "Bad market connection. " + message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
