package com.fundo.exception;


public class AccountCreationException extends Exception {
    private String message;

    public AccountCreationException(String message) {
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
