package com.example.post.models;

import java.util.UUID;

public class Movies {
    UUID id;
    String name;
    String director;
    String category;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public String getCategory() {
        return category;
    }

}
