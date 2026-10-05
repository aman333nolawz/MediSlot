package com.hospital;

import com.hospital.gui.MainFrame;
import com.hospital.util.DatabaseUtil;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    // Initialize database and tables
    SwingUtilities.invokeLater(() -> {
      try {
        // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

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
