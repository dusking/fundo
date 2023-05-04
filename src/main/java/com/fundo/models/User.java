package com.fundo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class User {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String username;

    public User() {
    }

    public User(String name) {
        this.username = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.username;
    }

    public void setName(final String name) {
        this.username = name;
    }
}