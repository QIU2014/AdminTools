package org.eric;

import javax.swing.*;
import java.awt.*;

public class GUILanding {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AdminTools Plugin - Instructions");
            frame.setSize(500, 320);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JTextArea text = new JTextArea();
            text.setEditable(false);
            text.setText(
                    "AdminTools Bukkit Plugin\n\n" +
                            "How to install:\n" +
                            "1. Place this JAR inside your server's /plugins/ folder.\n" +
                            "2. Start the server.\n\n" +
                            "Commands:\n" +
                            "  //admin vanish        Toggle vanish\n" +
                            "  //admin fly           Toggle flight\n" +
                            "  //admin search <p>    Multi-page inventory editor\n\n" +
                            "This window only appears when double-clicking the JAR.\n"
            );

            frame.add(new JScrollPane(text), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
