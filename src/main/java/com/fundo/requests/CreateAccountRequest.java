package com.fundo.requests;

import jakarta.validation.constraints.NotEmpty;


public class CreateAccountRequest {
    @NotEmpty(message = "username cannot be null or empty")
    public String username;

    public CreateAccountRequest(String username) {
        this.username = username;
    }
}
