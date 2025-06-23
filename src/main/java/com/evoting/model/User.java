/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evoting.model;

/**
 *
 * @author YusufDS
 */
import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String hashedPassword;
    private boolean hasVoted;
    private String role = "user"; // default role

    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.hasVoted = false;
        this.role = "user";
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public String getRole() {
        return role;
    }

    // Setter
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
