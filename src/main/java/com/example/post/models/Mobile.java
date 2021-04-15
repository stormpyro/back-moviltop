package com.example.post.models;

public class Mobile {
    private String model;
    private String year;
    private String factory;

    public void setModel(String model) {
        this.model = model;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public String getFactory() {
        return factory;
    }

    public String getYear() {
        return year;
    }
}
