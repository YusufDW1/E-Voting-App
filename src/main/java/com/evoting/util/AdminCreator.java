/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evoting.util;

/**
 *
 * @author YusufDS
 */
import com.evoting.crypto.PasswordHasher;
import com.evoting.model.User;
import com.evoting.db.MongoService;
import java.util.Scanner;

public class AdminCreator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Admin Creator ===");
        System.out.print("Masukkan username admin: ");
        String username = sc.nextLine();

        System.out.print("Masukkan password admin: ");
        String password = sc.nextLine();

        String hashed = PasswordHasher.hashPassword(password);
        User admin = new User(username, hashed);
        admin.setRole("admin");

        MongoService.saveUser(admin);

        System.out.println("✅ Admin berhasil dibuat: " + username);
    }
}
