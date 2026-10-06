package com.hospital.gui;

import com.hospital.service.HospitalService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private final HospitalService hospitalService;

  public MainFrame() {
    this.hospitalService = new HospitalService();
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Hospital Appointment Scheduling System");
    setSize(900, 650);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Header panel
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    headerPanel.setPreferredSize(new Dimension(900, 80));
    JLabel headerLabel = new JLabel("Hospital Appointment Management System");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);

    // Main content panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);

    // Button panel
    JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
    buttonPanel.setBackground(Color.WHITE);

    // Create buttons
    JButton patientButton = createStyledButton("Patient Management", "Manage Patients (Register, View, Update)");
    JButton doctorButton = createStyledButton("Doctor Management", "Manage Doctors (Add, View, Update)");
    JButton appointmentButton = createStyledButton("Appointment Management", "Schedule and Manage Appointments");
    JButton reportsButton = createStyledButton("Reports", "View System Reports");

    // Add action listeners
    patientButton.addActionListener(e -> openPatientManagement());
    doctorButton.addActionListener(e -> openDoctorManagement());
    appointmentButton.addActionListener(e -> openAppointmentManagement());
    reportsButton.addActionListener(e -> showReports());

    buttonPanel.add(patientButton);
    buttonPanel.add(doctorButton);
    buttonPanel.add(appointmentButton);
    buttonPanel.add(reportsButton);

    // Footer panel
    JPanel footerPanel = new JPanel();
    footerPanel.setBackground(new Color(240, 240, 240));
    footerPanel.setPreferredSize(new Dimension(900, 40));
    JLabel footerLabel = new JLabel("Hospital Management System - OOP Java Project");
    footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    footerPanel.add(footerLabel);

    mainPanel.add(buttonPanel, BorderLayout.CENTER);

    add(headerPanel, BorderLayout.NORTH);
    add(mainPanel, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);
  }

  private JButton createStyledButton(String text, String tooltip) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setPreferredSize(new Dimension(200, 100));
    button.setBackground(new Color(0, 153, 255));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setToolTipText(tooltip);

    // Hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(0, 123, 255));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(0, 153, 255));
      }
    });

    return button;
  }

  private void openPatientManagement() {
    new PatientManagementFrame(hospitalService).setVisible(true);
  }

  private void openDoctorManagement() {
    new DoctorManagementFrame(hospitalService).setVisible(true);
  }

  private void openAppointmentManagement() {
    new AppointmentManagementFrame(hospitalService).setVisible(true);
  }

  private void showReports() {
    new ReportsFrame(hospitalService).setVisible(true);
  }
}
