package com.fundo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class invalidMarketRequestException extends Exception {

    public invalidMarketRequestException() {
        super();
        this.message = "Invalid market request.";
    }

    public invalidMarketRequestException(String message) {
        super();
        this.message = "Invalid market request. " + message;
    }

    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
