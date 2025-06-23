/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evoting.net;

/**
 *
 * @author YusufDS
 */
import java.io.*;
import java.net.*;
import com.mongodb.client.*;
import org.bson.Document;

public class VoteClient extends Thread {

    private final String candidate;

    public VoteClient(String candidate) {
        this.candidate = candidate;
    }

    @Override
    public void run() {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = client.getDatabase("evoting");
            MongoCollection<Document> votes = db.getCollection("votes");

            Document vote = new Document("candidate", candidate);
            votes.insertOne(vote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
