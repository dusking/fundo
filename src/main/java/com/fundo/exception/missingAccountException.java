package com.fundo.exception;


public class missingAccountException extends Exception{
    private String message;

    public missingAccountException(String accountId) {
        super();
        this.message = String.format("Missing Account %s", accountId);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
