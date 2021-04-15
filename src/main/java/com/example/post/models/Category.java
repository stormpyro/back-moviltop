package com.example.post.models;

import java.util.UUID;

public class Category {
    UUID id;
    String name;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
