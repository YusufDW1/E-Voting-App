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

public class VoteClient {

    private String candidate;

    public VoteClient(String candidate) {
        this.candidate = candidate;
    }

    public void run() {
        try (Socket socket = new Socket("localhost", 5000); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(candidate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
