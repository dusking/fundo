package com.fundo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
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
