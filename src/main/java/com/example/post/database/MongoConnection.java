package com.example.post.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {

    private String _password;
    private String _defaultDb;

    public MongoConnection(String password, String defaultDb) {
        this._defaultDb = defaultDb;
        this._password = password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_defaultDb(String _defaultDb) {
        this._defaultDb = _defaultDb;
    }

    public MongoClient initClient() {
        System.out.println("Connected to MongoDB!!");
        String uri = "mongodb+srv://contacto:" + _password + "@cluster0.v3zeq.mongodb.net/" + _defaultDb
                + "?retryWrites=true&w=majority";
        MongoClientURI url = new MongoClientURI(uri);
        MongoClient client = new MongoClient(url);
        return client;
    }

}
