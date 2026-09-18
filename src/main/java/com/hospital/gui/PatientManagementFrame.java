package com.hospital.gui;

import com.hospital.model.Patient;
import com.hospital.service.HospitalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientManagementFrame extends JFrame {
    private final HospitalService hospitalService;
    private final DefaultTableModel tableModel;
    private final JTable patientTable;
    private final JTextField firstNameField, lastNameField, ageField, phoneField, emailField, addressField;
    private final JComboBox<String> genderComboBox;
    private int selectedPatientId = -1;
    
    public PatientManagementFrame(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setTitle("Patient Management");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Information"));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);
        
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);
        
        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);
        
        formPanel.add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(genderComboBox);
        
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("Add Patient");
        JButton updateButton = new JButton("Update Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> addPatient());
        updateButton.addActionListener(e -> updatePatient());
        deleteButton.addActionListener(e -> deletePatient());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> loadPatients());
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        
        // Table
        String[] columns = {"ID", "First Name", "Last Name", "Age", "Gender", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(tableModel);
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && patientTable.getSelectedRow() != -1) {
                loadSelectedPatient();
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        
        // Top panel combining form and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        
        loadPatients();
    }
    
    private void addPatient() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = (String) genderComboBox.getSelectedItem();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in required fields (First Name, Last Name, Phone)");
                return;
            }
            
            Patient patient = new Patient(firstName, lastName, age, gender, phone, email, address);
            if (hospitalService.registerPatient(patient)) {
                JOptionPane.showMessageDialog(this, "Patient registered successfully!");
                clearForm();
                loadPatients();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register patient!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid age!");
        }
    }
    
    private void updatePatient() {
        if (selectedPatientId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update!");
            return;
        }
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = (String) genderComboBox.getSelectedItem();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            
            Patient patient = hospitalService.getPatient(selectedPatientId);
            if (patient != null) {
                patient.setFirstName(firstName);
                patient.setLastName(lastName);
                patient.setAge(age);
                patient.setGender(gender);
                patient.setPhone(phone);
                patient.setEmail(email);
                patient.setAddress(address);
                if (hospitalService.updatePatient(patient)) {
                    JOptionPane.showMessageDialog(this, "Patient updated successfully!");
                    clearForm();
                    loadPatients();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update patient!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid age!");
        }
    }
    
    private void deletePatient() {
        if (selectedPatientId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete!");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this patient?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (hospitalService.removePatient(selectedPatientId)) {
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
                clearForm();
                loadPatients();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete patient!");
            }
        }
    }
    
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        genderComboBox.setSelectedIndex(0);
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        selectedPatientId = -1;
        patientTable.clearSelection();
    }
    
    private void loadSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedPatientId = (int) tableModel.getValueAt(selectedRow, 0);
            Patient patient = hospitalService.getPatient(selectedPatientId);
            if (patient != null) {
                firstNameField.setText(patient.getFirstName());
                lastNameField.setText(patient.getLastName());
                ageField.setText(String.valueOf(patient.getAge()));
                genderComboBox.setSelectedItem(patient.getGender());
                phoneField.setText(patient.getPhone());
                emailField.setText(patient.getEmail());
                addressField.setText(patient.getAddress());
            }
        }
    }
    
    private void loadPatients() {
        tableModel.setRowCount(0);
        List<Patient> patients = hospitalService.getAllPatients();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Patient patient : patients) {
            Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getAddress()
            };
            tableModel.addRow(row);
        }
    }
}
