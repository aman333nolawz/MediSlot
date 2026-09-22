package com.hospital.gui;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.HospitalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReportsFrame extends JFrame {
    private final HospitalService hospitalService;
    private final JTabbedPane tabbedPane;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public ReportsFrame(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setTitle("Reports");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        
        // Patients tab
        tabbedPane.addTab("All Patients", createPatientsPanel());
        
        // Doctors tab
        tabbedPane.addTab("All Doctors", createDoctorsPanel());
        
        // Appointments tab
        tabbedPane.addTab("All Appointments", createAppointmentsPanel());
        
        // Statistics tab
        tabbedPane.addTab("Statistics", createStatisticsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Full Name", "Age", "Gender", "Phone", "Email", "Registered Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        List<Patient> patients = hospitalService.getAllPatients();
        for (Patient patient : patients) {
            Object[] row = {
                    patient.getPatientId(),
                    patient.getFullName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhone(),
                    patient.getEmail(),
                    sdf.format(patient.getRegisteredDate())
            };
            model.addRow(row);
        }
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createDoctorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Full Name", "Specialization", "Phone", "Email", "Experience", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        List<Doctor> doctors = hospitalService.getAllDoctors();
        for (Doctor doctor : doctors) {
            Object[] row = {
                    doctor.getDoctorId(),
                    doctor.getFullName(),
                    doctor.getSpecialization(),
                    doctor.getPhone(),
                    doctor.getEmail(),
                    doctor.getExperienceYears(),
                    doctor.isAvailable() ? "Yes" : "No"
            };
            model.addRow(row);
        }
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient", "Doctor", "Date", "Time Slot", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        List<Appointment> appointments = hospitalService.getAllAppointments();
        for (Appointment appointment : appointments) {
            Patient patient = hospitalService.getPatient(appointment.getPatientId());
            Doctor doctor = hospitalService.getDoctor(appointment.getDoctorId());
            Object[] row = {
                    appointment.getAppointmentId(),
                    patient != null ? patient.getFullName() : "N/A",
                    doctor != null ? doctor.getFullName() : "N/A",
                    sdf.format(appointment.getAppointmentDate()),
                    appointment.getTimeSlot(),
                    appointment.getReason(),
                    appointment.getStatus().toString()
            };
            model.addRow(row);
        }
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(Color.WHITE);
        
        int totalPatients = hospitalService.getAllPatients().size();
        int totalDoctors = hospitalService.getAllDoctors().size();
        int availableDoctors = hospitalService.getAvailableDoctors().size();
        int totalAppointments = hospitalService.getAllAppointments().size();
        
        JLabel patientsLabel = new JLabel("Total Patients:");
        patientsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel patientsValue = new JLabel(String.valueOf(totalPatients));
        patientsValue.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel doctorsLabel = new JLabel("Total Doctors:");
        doctorsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel doctorsValue = new JLabel(String.valueOf(totalDoctors));
        doctorsValue.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel availableDoctorsLabel = new JLabel("Available Doctors:");
        availableDoctorsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel availableDoctorsValue = new JLabel(String.valueOf(availableDoctors));
        availableDoctorsValue.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel appointmentsLabel = new JLabel("Total Appointments:");
        appointmentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel appointmentsValue = new JLabel(String.valueOf(totalAppointments));
        appointmentsValue.setFont(new Font("Arial", Font.PLAIN, 16));
        
        panel.add(patientsLabel);
        panel.add(patientsValue);
        panel.add(doctorsLabel);
        panel.add(doctorsValue);
        panel.add(availableDoctorsLabel);
        panel.add(availableDoctorsValue);
        panel.add(appointmentsLabel);
        panel.add(appointmentsValue);
        
        return panel;
    }
}
