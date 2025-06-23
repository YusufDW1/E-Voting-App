/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evoting.db;

/**
 *
 * @author YusufDS
 */
import com.evoting.model.User;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

public class MongoService {

    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private static final MongoDatabase db = client.getDatabase("evoting");
    private static final MongoCollection<Document> users = db.getCollection("users");

    public static void saveUser(User user) {
        Document doc = new Document("username", user.getUsername())
                .append("password", user.getHashedPassword())
                .append("hasVoted", user.isHasVoted());
        users.insertOne(doc);
    }

    public static User getUserByUsername(String username) {
        Document doc = users.find(eq("username", username)).first();
        if (doc != null) {
            User user = new User(
                    doc.getString("username"),
                    doc.getString("password")
            );
            user.setHasVoted(doc.getBoolean("hasVoted", false));
            user.setRole(doc.getString("role")); // pastikan role ada di DB
            return user;
        }
        return null;
    }
}
