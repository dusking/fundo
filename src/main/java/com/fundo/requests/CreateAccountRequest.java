package com.fundo.requests;

import jakarta.validation.constraints.NotEmpty;


public class CreateAccountRequest {
    @NotEmpty(message = "username cannot be null or empty")
    public String ownerName;

    public CreateAccountRequest() {}

    public CreateAccountRequest(String ownerName) {
        this.ownerName = ownerName;
    }
}
