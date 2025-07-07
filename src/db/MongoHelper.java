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

public class MongoHelper {

    private static final String DB_NAME = "voting_db";
    private static final String USER_COLLECTION = "users";
    private static final String CANDIDATE_COLLECTION = "candidates";

    private static final MongoClient mongoClient = MongoClients.create();
    private static final MongoDatabase database = mongoClient.getDatabase(DB_NAME);
    private static final MongoCollection<Document> userCol = database.getCollection(USER_COLLECTION);
    private static final MongoCollection<Document> candidateCol = database.getCollection(CANDIDATE_COLLECTION);

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

    // Tambah 1 vote ke kandidat
    public static void incrementVote(String candidateName) {
        candidateCol.updateOne(
                Filters.eq("name", candidateName),
                Updates.inc("vote_count", 1)
        );
    }
}
