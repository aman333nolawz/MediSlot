package com.hospital.gui;

import com.hospital.model.Doctor;
import com.hospital.service.HospitalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorManagementFrame extends JFrame {
    private final HospitalService hospitalService;
    private final DefaultTableModel tableModel;
    private final JTable doctorTable;
    private final JTextField firstNameField, lastNameField, specializationField, phoneField, emailField, experienceField;
    private final JCheckBox availableCheckBox;
    private int selectedDoctorId = -1;
    
    public DoctorManagementFrame(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setTitle("Doctor Management");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Doctor Information"));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);
        
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);
        
        formPanel.add(new JLabel("Specialization:"));
        specializationField = new JTextField();
        formPanel.add(specializationField);
        
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Experience (Years):"));
        experienceField = new JTextField();
        formPanel.add(experienceField);
        
        formPanel.add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        availableCheckBox.setSelected(true);
        formPanel.add(availableCheckBox);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("Add Doctor");
        JButton updateButton = new JButton("Update Doctor");
        JButton deleteButton = new JButton("Delete Doctor");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> addDoctor());
        updateButton.addActionListener(e -> updateDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> loadDoctors());
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        
        // Table
        String[] columns = {"ID", "First Name", "Last Name", "Specialization", "Phone", "Email", "Experience", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        doctorTable = new JTable(tableModel);
        doctorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && doctorTable.getSelectedRow() != -1) {
                loadSelectedDoctor();
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(doctorTable);
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        
        loadDoctors();
    }
    
    private void addDoctor() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String specialization = specializationField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            int experience = Integer.parseInt(experienceField.getText().trim());
            boolean available = availableCheckBox.isSelected();
            
            if (firstName.isEmpty() || lastName.isEmpty() || specialization.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in required fields!");
                return;
            }
            
            Doctor doctor = new Doctor(firstName, lastName, specialization, phone, email, experience);
            doctor.setAvailable(available);
            if (hospitalService.addDoctor(doctor)) {
                JOptionPane.showMessageDialog(this, "Doctor added successfully!");
                clearForm();
                loadDoctors();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add doctor!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid experience years!");
        }
    }
    
    private void updateDoctor() {
        if (selectedDoctorId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to update!");
            return;
        }
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String specialization = specializationField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            int experience = Integer.parseInt(experienceField.getText().trim());
            boolean available = availableCheckBox.isSelected();
            
            Doctor doctor = hospitalService.getDoctor(selectedDoctorId);
            if (doctor != null) {
                doctor.setFirstName(firstName);
                doctor.setLastName(lastName);
                doctor.setSpecialization(specialization);
                doctor.setPhone(phone);
                doctor.setEmail(email);
                doctor.setExperienceYears(experience);
                doctor.setAvailable(available);
                if (hospitalService.updateDoctor(doctor)) {
                    JOptionPane.showMessageDialog(this, "Doctor updated successfully!");
                    clearForm();
                    loadDoctors();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update doctor!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid experience years!");
        }
    }
    
    private void deleteDoctor() {
        if (selectedDoctorId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete!");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this doctor?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (hospitalService.removeDoctor(selectedDoctorId)) {
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
                clearForm();
                loadDoctors();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete doctor!");
            }
        }
    }
    
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        specializationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        experienceField.setText("");
        availableCheckBox.setSelected(true);
        selectedDoctorId = -1;
        doctorTable.clearSelection();
    }
    
    private void loadSelectedDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedDoctorId = (int) tableModel.getValueAt(selectedRow, 0);
            Doctor doctor = hospitalService.getDoctor(selectedDoctorId);
            if (doctor != null) {
                firstNameField.setText(doctor.getFirstName());
                lastNameField.setText(doctor.getLastName());
                specializationField.setText(doctor.getSpecialization());
                phoneField.setText(doctor.getPhone());
                emailField.setText(doctor.getEmail());
                experienceField.setText(String.valueOf(doctor.getExperienceYears()));
                availableCheckBox.setSelected(doctor.isAvailable());
            }
        }
    }
    
    private void loadDoctors() {
        tableModel.setRowCount(0);
        List<Doctor> doctors = hospitalService.getAllDoctors();
        for (Doctor doctor : doctors) {
            Object[] row = {
                    doctor.getDoctorId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getSpecialization(),
                    doctor.getPhone(),
                    doctor.getEmail(),
                    doctor.getExperienceYears(),
                    doctor.isAvailable() ? "Yes" : "No"
            };
            tableModel.addRow(row);
        }
    }
}
