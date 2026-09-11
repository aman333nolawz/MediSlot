package com.hospital.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum Status {
        SCHEDULED, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW
    }
    
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private String timeSlot;
    private String reason;
    private Status status;
    private Date createdDate;
    
    public Appointment() {
        this.createdDate = new Date();
        this.status = Status.SCHEDULED;
    }
    
    public Appointment(int patientId, int doctorId, Date appointmentDate, String timeSlot, String reason) {
        this();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.reason = reason;
    }
    
    public Appointment(int appointmentId, int patientId, int doctorId, Date appointmentDate, String timeSlot, String reason, Status status, Date createdDate) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.reason = reason;
        this.status = status;
        this.createdDate = createdDate;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public int getPatientId() {
        return patientId;
    }
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public int getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public String getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return appointmentId == that.appointmentId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(appointmentId);
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentDate=" + appointmentDate +
                ", timeSlot='" + timeSlot + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}
