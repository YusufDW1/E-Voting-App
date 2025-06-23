/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evoting;

/**
 *
 * @author YusufDS
 */
import com.evoting.gui.LoginFrame;
import javax.swing.SwingUtilities;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("id"));

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
