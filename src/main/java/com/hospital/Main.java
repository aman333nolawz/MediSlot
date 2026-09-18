package com.hospital;

import com.hospital.gui.MainFrame;
import com.hospital.util.DatabaseUtil;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize database and tables
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Initialize database
                DatabaseUtil.createDatabase();
                DatabaseUtil.initializeTables();
                
                // Start GUI
                new MainFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
