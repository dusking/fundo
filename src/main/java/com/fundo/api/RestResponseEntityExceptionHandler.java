package com.fundo.api;

import com.fundo.exception.AccountCreationException;
import com.fundo.exception.InsufficientFundsException;
import com.fundo.exception.MissingUserException;
import com.fundo.exception.missingAccountException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Runtime Exception";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { AccountCreationException.class })
    protected ResponseEntity<Object> handleAccountCreation(Exception ex, WebRequest request) {
        String bodyOfResponse = "Unable to create account";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MissingUserException.class})
    protected ResponseEntity<Object> handleMissingUser(Exception ex, WebRequest request) {
        String bodyOfResponse = "Missing User";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {missingAccountException.class})
    protected ResponseEntity<Object> handleMissingAccount(Exception ex, WebRequest request) {
        String bodyOfResponse = "Missing Account";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {InsufficientFundsException.class})
    protected ResponseEntity<Object> handleInsufficientFundsException(Exception ex, WebRequest request) {
        String bodyOfResponse = "Insufficient Funds in Account";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
