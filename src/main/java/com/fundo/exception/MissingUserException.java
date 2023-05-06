package com.fundo.exception;


public class MissingUserException extends Exception {
    private String message;

    public MissingUserException(String username) {
        super();
        this.message = String.format("Missing User %s", username);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
