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

public class VoteServer {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Vote server ready...");
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String candidate = in.readLine();
                System.out.println("Vote for: " + candidate);
                // Simpan ke MongoDB
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
