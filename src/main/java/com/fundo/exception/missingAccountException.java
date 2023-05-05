package com.fundo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
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
