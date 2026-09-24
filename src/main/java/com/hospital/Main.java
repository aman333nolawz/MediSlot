package com.hospital;

import com.hospital.gui.MainFrame;
import com.hospital.util.DatabaseUtil;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize database and tables
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel to Nimbus for consistent Swing styling
                boolean nimbusFound = false;
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        nimbusFound = true;
                        break;
                    }
                }
                if (!nimbusFound) {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                }
                
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
