package com.hospital.gui;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.HospitalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentManagementFrame extends JFrame {
    private final HospitalService hospitalService;
    private final DefaultTableModel tableModel;
    private final JTable appointmentTable;
    private final JComboBox<PatientComboItem> patientComboBox;
    private final JComboBox<DoctorComboItem> doctorComboBox;
    private final JTextField dateField, timeSlotField, reasonField;
    private final JComboBox<String> statusComboBox;
    private int selectedAppointmentId = -1;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public AppointmentManagementFrame(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setTitle("Appointment Management");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Appointment Information"));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("Patient:"));
        patientComboBox = new JComboBox<>();
        loadPatients();
        formPanel.add(patientComboBox);
        
        formPanel.add(new JLabel("Doctor:"));
        doctorComboBox = new JComboBox<>();
        loadDoctors();
        formPanel.add(doctorComboBox);
        
        formPanel.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        dateField = new JTextField(sdf.format(new Date()));
        formPanel.add(dateField);
        
        formPanel.add(new JLabel("Time Slot (e.g., 09:00-10:00):"));
        timeSlotField = new JTextField();
        formPanel.add(timeSlotField);
        
        formPanel.add(new JLabel("Reason:"));
        reasonField = new JTextField();
        formPanel.add(reasonField);
        
        formPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"SCHEDULED", "CONFIRMED", "COMPLETED", "CANCELLED", "NO_SHOW"});
        formPanel.add(statusComboBox);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton scheduleButton = new JButton("Schedule Appointment");
        JButton updateButton = new JButton("Update Appointment");
        JButton cancelButton = new JButton("Cancel Appointment");
        JButton deleteButton = new JButton("Delete Appointment");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");
        JButton checkAvailabilityButton = new JButton("Check Availability");
        
        scheduleButton.addActionListener(e -> scheduleAppointment());
        updateButton.addActionListener(e -> updateAppointment());
        cancelButton.addActionListener(e -> cancelAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> loadAppointments());
        checkAvailabilityButton.addActionListener(e -> checkAvailability());
        
        buttonPanel.add(scheduleButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(checkAvailabilityButton);
        
        // Table
        String[] columns = {"ID", "Patient ID", "Patient Name", "Doctor ID", "Doctor Name", "Date", "Time Slot", "Reason", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(tableModel);
        appointmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && appointmentTable.getSelectedRow() != -1) {
                loadSelectedAppointment();
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        
        loadAppointments();
    }
    
    private void scheduleAppointment() {
        try {
            PatientComboItem patientItem = (PatientComboItem) patientComboBox.getSelectedItem();
            DoctorComboItem doctorItem = (DoctorComboItem) doctorComboBox.getSelectedItem();
            Date appointmentDate = sdf.parse(dateField.getText().trim());
            String timeSlot = timeSlotField.getText().trim();
            String reason = reasonField.getText().trim();
            
            if (patientItem == null || doctorItem == null || timeSlot.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
                return;
            }
            
            if (hospitalService.scheduleAppointment(patientItem.getId(), doctorItem.getId(), appointmentDate, timeSlot, reason)) {
                JOptionPane.showMessageDialog(this, "Appointment scheduled successfully!");
                clearForm();
                loadAppointments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to schedule appointment! Time slot may not be available.");
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter date in YYYY-MM-DD format!");
        }
    }
    
    private void updateAppointment() {
        if (selectedAppointmentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update!");
            return;
        }
        try {
            PatientComboItem patientItem = (PatientComboItem) patientComboBox.getSelectedItem();
            DoctorComboItem doctorItem = (DoctorComboItem) doctorComboBox.getSelectedItem();
            Date appointmentDate = sdf.parse(dateField.getText().trim());
            String timeSlot = timeSlotField.getText().trim();
            String reason = reasonField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            
            Appointment appointment = hospitalService.getAppointment(selectedAppointmentId);
            if (appointment != null) {
                appointment.setPatientId(patientItem.getId());
                appointment.setDoctorId(doctorItem.getId());
                appointment.setAppointmentDate(appointmentDate);
                appointment.setTimeSlot(timeSlot);
                appointment.setReason(reason);
                appointment.setStatus(Appointment.Status.valueOf(status));
                if (hospitalService.updateAppointment(appointment)) {
                    JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
                    clearForm();
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update appointment!");
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter date in YYYY-MM-DD format!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid status!");
        }
    }
    
    private void cancelAppointment() {
        if (selectedAppointmentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel!");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this appointment?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (hospitalService.cancelAppointment(selectedAppointmentId)) {
                JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!");
                clearForm();
                loadAppointments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel appointment!");
            }
        }
    }
    
    private void deleteAppointment() {
        if (selectedAppointmentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete!");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this appointment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (hospitalService.deleteAppointment(selectedAppointmentId)) {
                JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");
                clearForm();
                loadAppointments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete appointment!");
            }
        }
    }
    
    private void checkAvailability() {
        try {
            DoctorComboItem doctorItem = (DoctorComboItem) doctorComboBox.getSelectedItem();
            Date appointmentDate = sdf.parse(dateField.getText().trim());
            String timeSlot = timeSlotField.getText().trim();
            
            if (doctorItem == null || timeSlot.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select doctor, enter date and time slot!");
                return;
            }
            
            if (hospitalService.checkAvailability(doctorItem.getId(), appointmentDate, timeSlot)) {
                JOptionPane.showMessageDialog(this, "Time slot is AVAILABLE!");
            } else {
                JOptionPane.showMessageDialog(this, "Time slot is NOT AVAILABLE!");
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter date in YYYY-MM-DD format!");
        }
    }
    
    private void clearForm() {
        patientComboBox.setSelectedIndex(0);
        doctorComboBox.setSelectedIndex(0);
        dateField.setText(sdf.format(new Date()));
        timeSlotField.setText("");
        reasonField.setText("");
        statusComboBox.setSelectedIndex(0);
        selectedAppointmentId = -1;
        appointmentTable.clearSelection();
    }
    
    private void loadSelectedAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedAppointmentId = (int) tableModel.getValueAt(selectedRow, 0);
            Appointment appointment = hospitalService.getAppointment(selectedAppointmentId);
            if (appointment != null) {
                // Select patient
                for (int i = 0; i < patientComboBox.getItemCount(); i++) {
                    if (patientComboBox.getItemAt(i).getId() == appointment.getPatientId()) {
                        patientComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                // Select doctor
                for (int i = 0; i < doctorComboBox.getItemCount(); i++) {
                    if (doctorComboBox.getItemAt(i).getId() == appointment.getDoctorId()) {
                        doctorComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                dateField.setText(sdf.format(appointment.getAppointmentDate()));
                timeSlotField.setText(appointment.getTimeSlot());
                reasonField.setText(appointment.getReason());
                statusComboBox.setSelectedItem(appointment.getStatus().toString());
            }
        }
    }
    
    private void loadAppointments() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = hospitalService.getAllAppointments();
        for (Appointment appointment : appointments) {
            Patient patient = hospitalService.getPatient(appointment.getPatientId());
            Doctor doctor = hospitalService.getDoctor(appointment.getDoctorId());
            String patientName = patient != null ? patient.getFullName() : "N/A";
            String doctorName = doctor != null ? doctor.getFullName() : "N/A";
            Object[] row = {
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    patientName,
                    appointment.getDoctorId(),
                    doctorName,
                    sdf.format(appointment.getAppointmentDate()),
                    appointment.getTimeSlot(),
                    appointment.getReason(),
                    appointment.getStatus().toString()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadPatients() {
        patientComboBox.removeAllItems();
        List<Patient> patients = hospitalService.getAllPatients();
        for (Patient patient : patients) {
            patientComboBox.addItem(new PatientComboItem(patient.getPatientId(), patient.getFullName()));
        }
    }
    
    private void loadDoctors() {
        doctorComboBox.removeAllItems();
        List<Doctor> doctors = hospitalService.getAllDoctors();
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(new DoctorComboItem(doctor.getDoctorId(), doctor.getFullName() + " (" + doctor.getSpecialization() + ")"));
        }
    }
    
    // Helper classes for combo boxes
    private static class PatientComboItem {
        private final int id;
        private final String name;
        public PatientComboItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() { return id; }
        @Override public String toString() { return id + " - " + name; }
    }
    
    private static class DoctorComboItem {
        private final int id;
        private final String name;
        public DoctorComboItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() { return id; }
        @Override public String toString() { return id + " - " + name; }
    }
}
