package com.hospital.dao;

import com.hospital.model.Appointment;
import com.hospital.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAO {
    
    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, time_slot, reason, status, created_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(4, appointment.getTimeSlot());
            pstmt.setString(5, appointment.getReason());
            pstmt.setString(6, appointment.getStatus().toString());
            pstmt.setTimestamp(7, new Timestamp(appointment.getCreatedDate().getTime()));
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        appointment.setAppointmentId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAppointment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC, time_slot";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY appointment_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_date DESC, time_slot";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByDate(Date date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date = ? ORDER BY time_slot";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, time_slot = ?, reason = ?, status = ? WHERE appointment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(4, appointment.getTimeSlot());
            pstmt.setString(5, appointment.getReason());
            pstmt.setString(6, appointment.getStatus().toString());
            pstmt.setInt(7, appointment.getAppointmentId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean cancelAppointment(int appointmentId) {
        String sql = "UPDATE appointments SET status = 'CANCELLED' WHERE appointment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isTimeSlotAvailable(int doctorId, Date date, String timeSlot) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ? AND time_slot = ? AND status != 'CANCELLED'";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            pstmt.setString(3, timeSlot);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setTimeSlot(rs.getString("time_slot"));
        appointment.setReason(rs.getString("reason"));
        appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
        appointment.setCreatedDate(rs.getTimestamp("created_date"));
        return appointment;
    }
}
