package com.fundo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class MarketConnectionException extends Exception {
    public MarketConnectionException() {
        super();
        this.message = "Invalid market request.";
    }

    public MarketConnectionException(String message) {
        super();
        this.message = "Bad market connection. " + message;
    }

    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
