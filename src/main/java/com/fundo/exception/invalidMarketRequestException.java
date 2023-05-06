package com.fundo.exception;


public class invalidMarketRequestException extends Exception {
    private String message;

    public invalidMarketRequestException() {
        super();
        this.message = "Invalid market request.";
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
