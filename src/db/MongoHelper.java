/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author YusufDS
 */
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.Candidate;
import model.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MongoHelper {

    private static final MongoClient mongoClient = MongoClients.create();
    private static final MongoDatabase database = mongoClient.getDatabase("voting_db");
    private static final MongoCollection<Document> userCol = database.getCollection("users");
    private static final MongoCollection<Document> candidateCol = database.getCollection("candidates");

    // Tambah user baru
    public static void insertUser(User user) {
        Document doc = new Document("username", user.getUsername())
                .append("password_hash", user.getPasswordHash())
                .append("voted", false)
                .append("voted_candidate", null);
        userCol.insertOne(doc);
    }

    // Ambil user berdasarkan username
    public static User getUser(String username) {
        Document doc = userCol.find(Filters.eq("username", username)).first();
        if (doc == null) {
            return null;
        }

        User user = new User(doc.getString("username"), doc.getString("password_hash"));
        user.setVoted(doc.getBoolean("voted"));
        user.setVotedCandidate(doc.getString("voted_candidate"));
        return user;
    }

    // Update status voted user
    public static void setUserVoted(String username, String candidateName) {
        userCol.updateOne(Filters.eq("username", username),
                Updates.combine(
                        Updates.set("voted", true),
                        Updates.set("voted_candidate", candidateName)
                ));
    }

    // Ambil semua kandidat
    public static List<Candidate> getAllCandidates() {
        List<Candidate> list = new ArrayList<>();
        for (Document doc : candidateCol.find()) {
            Candidate c = new Candidate(doc.getString("name"));
            // Set vote_count via refleksi atau method tambahan
            for (int i = 0; i < doc.getInteger("vote_count"); i++) {
                c.incrementVote(); // trik karena voteCount nggak punya setter
            }
            list.add(c);
        }
        return list;
    }
    
    public static MongoCollection<Document> getCandidateCollection() {
        return candidateCol;
    }

    // Tambah 1 vote ke kandidat
    public static void incrementVote(String candidateName) {
        candidateCol.updateOne(
                Filters.eq("name", candidateName),
                Updates.inc("vote_count", 1)
        );
    }

    public static Candidate getCandidateByName(String name) {
        Document doc = candidateCol.find(Filters.eq("name", name)).first();
        if (doc == null) {
            return null;
        }

        Candidate c = new Candidate(doc.getString("name"));
        for (int i = 0; i < doc.getInteger("vote_count", 0); i++) {
            c.incrementVote();
        }
        return c;
    }

    public static void addCandidate(Candidate candidate) {
        Document doc = new Document("name", candidate.getName())
                .append("vote_count", candidate.getVoteCount());
        candidateCol.insertOne(doc);
    }

    public static void updateCandidate(String oldName, String newName) {
        candidateCol.updateOne(
                Filters.eq("name", oldName),
                Updates.set("name", newName)
        );

    }

    public static void deleteCandidate(String name) {
        candidateCol.deleteOne(Filters.eq("name", name));
    }

}
