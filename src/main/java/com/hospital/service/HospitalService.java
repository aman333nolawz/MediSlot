package com.hospital.service;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;

import java.util.Date;
import java.util.List;

public class HospitalService {
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final AppointmentDAO appointmentDAO;
    
    public HospitalService() {
        this.patientDAO = new PatientDAO();
        this.doctorDAO = new DoctorDAO();
        this.appointmentDAO = new AppointmentDAO();
    }
    
    // Patient operations
    public boolean registerPatient(Patient patient) {
        if (patient == null) {
            return false;
        }
        return patientDAO.addPatient(patient);
    }
    
    public Patient getPatient(int patientId) {
        return patientDAO.getPatientById(patientId);
    }
    
    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }
    
    public boolean updatePatient(Patient patient) {
        if (patient == null || patient.getPatientId() <= 0) {
            return false;
        }
        return patientDAO.updatePatient(patient);
    }
    
    public boolean removePatient(int patientId) {
        return patientDAO.deletePatient(patientId);
    }
    
    // Doctor operations
    public boolean addDoctor(Doctor doctor) {
        if (doctor == null) {
            return false;
        }
        return doctorDAO.addDoctor(doctor);
    }
    
    public Doctor getDoctor(int doctorId) {
        return doctorDAO.getDoctorById(doctorId);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }
    
    public List<Doctor> getAvailableDoctors() {
        return doctorDAO.getAvailableDoctors();
    }
    
    public boolean updateDoctor(Doctor doctor) {
        if (doctor == null || doctor.getDoctorId() <= 0) {
            return false;
        }
        return doctorDAO.updateDoctor(doctor);
    }
    
    public boolean removeDoctor(int doctorId) {
        return doctorDAO.deleteDoctor(doctorId);
    }
    
    public boolean setDoctorAvailability(int doctorId, boolean available) {
        return doctorDAO.setAvailability(doctorId, available);
    }
    
    // Appointment operations
    public boolean scheduleAppointment(int patientId, int doctorId, Date appointmentDate, String timeSlot, String reason) {
        // Check if time slot is available
        if (!appointmentDAO.isTimeSlotAvailable(doctorId, appointmentDate, timeSlot)) {
            return false;
        }
        Appointment appointment = new Appointment(patientId, doctorId, appointmentDate, timeSlot, reason);
        return appointmentDAO.addAppointment(appointment);
    }
    
    public Appointment getAppointment(int appointmentId) {
        return appointmentDAO.getAppointmentById(appointmentId);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }
    
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return appointmentDAO.getAppointmentsByPatient(patientId);
    }
    
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }
    
    public List<Appointment> getAppointmentsByDate(Date date) {
        return appointmentDAO.getAppointmentsByDate(date);
    }
    
    public boolean updateAppointment(Appointment appointment) {
        if (appointment == null || appointment.getAppointmentId() <= 0) {
            return false;
        }
        return appointmentDAO.updateAppointment(appointment);
    }
    
    public boolean cancelAppointment(int appointmentId) {
        return appointmentDAO.cancelAppointment(appointmentId);
    }
    
    public boolean deleteAppointment(int appointmentId) {
        return appointmentDAO.deleteAppointment(appointmentId);
    }
    
    public boolean checkAvailability(int doctorId, Date date, String timeSlot) {
        return appointmentDAO.isTimeSlotAvailable(doctorId, date, timeSlot);
    }
}
