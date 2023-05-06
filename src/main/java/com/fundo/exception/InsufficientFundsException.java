package com.fundo.exception;


public class InsufficientFundsException extends Exception {
    private String message;

    public InsufficientFundsException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
