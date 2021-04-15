package com.example.post.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.post.database.MongoConnection;
import com.example.post.models.Category;
import com.example.post.models.Errors;
import com.example.post.models.Mobile;
import com.example.post.models.Movies;
import com.example.post.models.Session;
import com.example.post.models.UserLogin;
import com.example.post.models.UserLogout;
import com.example.post.models.UserRegister;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.bson.Document;
import org.springframework.http.ResponseEntity;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController

@RequestMapping("/")
public class controller {
    private MongoConnection mongo = new MongoConnection("tOTq2hBNZTACT1fg", "Tmobile");
    private MongoDatabase db = mongo.initClient().getDatabase("Tmobile");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private ArrayList<Movies> ArrMovies = new ArrayList<Movies>();
    private ArrayList<Category> ArrCategories = new ArrayList<Category>();

    @GetMapping("test")
    public ResponseEntity<Object> Test() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("getmobiles")
    public ResponseEntity<Object> GetMobiles() {
        MongoCollection<Document> c = db.getCollection("Mobiles");
        Document query = new Document();
        List<Document> allMobiles = new ArrayList<>();
        c.find(query).into(allMobiles);
        return ResponseEntity.ok().body(allMobiles);
    }

    @PostMapping("login")
    public ResponseEntity<Object> Login(@RequestBody UserLogin usr) {
        System.out.println("------------------------------Login------------------");
        MongoCollection<Document> c = db.getCollection("Users");
        String email = usr.getEmail();
        String password = usr.getPassword();
        Document query = new Document().append("email", email).append("password", password);

        List<Document> findResults = new ArrayList<>();
        c.find(query).into(findResults);
        if (findResults.size() > 0) {
            var session = new Session();
            var ID = UUID.randomUUID();
            var SSID = UUID.randomUUID();
            session.setID(ID);
            session.setSSID(SSID);
            session.setEmail(email);
            session.setEnabled(true);
            session.setStartDate(dtf.format(LocalDateTime.now()));
            session.setEndDate(dtf.format(LocalDateTime.now()));
            session.setCreatedDate(dtf.format(LocalDateTime.now()));
            session.setLastUpdateDate(dtf.format(LocalDateTime.now()));
            MongoCollection<Document> s = db.getCollection("Sessions");
            Document newSession = new Document().append("ID", session.getID()).append("SSID", session.getSSID())
                    .append("email", session.getEmail()).append("enabled", session.getEnabled())
                    .append("startDate", session.getStartDate()).append("endDate", session.getEndDate())
                    .append("createdDate", session.getCreatedDate())
                    .append("lastUpdateDate", session.getLastUpdateDate());
            s.insertOne(newSession);
            return ResponseEntity.ok().body(session);

        }

        System.out.println("User doesn't exists.");
        Errors err = new Errors(404, "This user doesn't exists in Tmobile.");
        return ResponseEntity.status(err.getStatusCode()).body(err);
        // Validar si el usr existe en mongodb
        // Si existe crear una session
        // Si no existe mandarle un error de usuario no registrado

    }

    @PostMapping("register")
    public ResponseEntity<Object> Register(@RequestBody UserRegister usr) {
        System.out.println("------------------------------Register------------------");
        MongoCollection<Document> c = db.getCollection("Users");
        String username = usr.getUsername();
        String email = usr.getEmail();
        String password = usr.getPassword();
        Document query = new Document().append("username", username).append("email", email).append("password",
                password);
        List<Document> findResults = new ArrayList<>();
        c.find(query).into(findResults);

        if (findResults.size() > 0) {
            System.out.println("User exists");
            Errors err = new Errors(409, "This user already exists.");
            return ResponseEntity.status(err.getStatusCode()).body(err);
        }

        // Validar si el usr no existe en mongodb
        // Si existe mandar un error que el usuario ya está registrado
        // Si no existe agregar el usuario a mongodb y crearle una sesión
        Document newUser = new Document().append("username", username).append("email", email).append("password",
                password);
        c.insertOne(newUser);

        MongoCollection<Document> s = db.getCollection("Sessions");
        var session = new Session();
        var ID = UUID.randomUUID();
        var SSID = UUID.randomUUID();
        session.setID(ID);
        session.setSSID(SSID);
        session.setEmail(email);
        session.setEnabled(true);
        session.setStartDate(dtf.format(LocalDateTime.now()));
        session.setEndDate(dtf.format(LocalDateTime.now()));
        session.setCreatedDate(dtf.format(LocalDateTime.now()));
        session.setLastUpdateDate(dtf.format(LocalDateTime.now()));
        Document newSession = new Document().append("ID", session.getID()).append("SSID", session.getSSID())
                .append("email", session.getEmail()).append("enabled", session.getEnabled())
                .append("startDate", session.getStartDate()).append("endDate", session.getEndDate())
                .append("createdDate", session.getCreatedDate()).append("lastUpdateDate", session.getLastUpdateDate());
        s.insertOne(newSession);
        return ResponseEntity.ok().body(session);

    }

    @PostMapping("logout")
    public ResponseEntity<Object> Logout(@RequestBody UserLogout usr) {
        System.out.println("------------------------------Logout------------------");
        MongoCollection<Document> c = db.getCollection("Sessions");
        String email = usr.getEmail();
        Document query = new Document().append("email", email).append("enabled", true);
        List<Document> findResults = new ArrayList<>();
        c.find(query).into(findResults);
        if (findResults.size() > 0) {
            c.findOneAndDelete(query);
            return ResponseEntity.ok().body("GoodBye");
        }
        System.out.println("Session not found");
        Errors err = new Errors(401, "You are an unauthorized user.");
        return ResponseEntity.status(err.getStatusCode()).body(err);
    }

    @PostMapping("addmovies")
    public ResponseEntity<Object> PostMovie(@RequestBody Movies mv) {
        mv.setId(UUID.randomUUID());
        ArrMovies.add(mv);
        return ResponseEntity.ok().body(mv);
    }

    @GetMapping("getmovies")
    public ResponseEntity<Object> GetMovies() {
        return ResponseEntity.ok().body(ArrMovies);
    }

    @PostMapping("addcategory")
    public ResponseEntity<Object> PostCategory(@RequestBody Category ct) {
        ct.setId(UUID.randomUUID());
        ArrCategories.add(ct);
        return ResponseEntity.ok().body(ct);
    }

    @GetMapping("getcategories")
    public ResponseEntity<Object> GetCategories() {
        return ResponseEntity.ok().body(ArrCategories);
    }

}
