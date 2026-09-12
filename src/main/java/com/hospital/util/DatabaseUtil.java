package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void createDatabase() {
        String createDBUrl = "jdbc:mysql://localhost:3306?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try (Connection conn = DriverManager.getConnection(createDBUrl, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS hospital_db");
            System.out.println("Database created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }
    
    public static void initializeTables() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                    "patient_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "first_name VARCHAR(50) NOT NULL," +
                    "last_name VARCHAR(50) NOT NULL," +
                    "age INT NOT NULL," +
                    "gender VARCHAR(10) NOT NULL," +
                    "phone VARCHAR(15) NOT NULL," +
                    "email VARCHAR(100)," +
                    "address VARCHAR(255)," +
                    "registered_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            
            String createDoctorsTable = "CREATE TABLE IF NOT EXISTS doctors (" +
                    "doctor_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "first_name VARCHAR(50) NOT NULL," +
                    "last_name VARCHAR(50) NOT NULL," +
                    "specialization VARCHAR(100) NOT NULL," +
                    "phone VARCHAR(15) NOT NULL," +
                    "email VARCHAR(100)," +
                    "experience_years INT NOT NULL," +
                    "available BOOLEAN DEFAULT TRUE" +
                    ")";
            
            String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "appointment_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "patient_id INT NOT NULL," +
                    "doctor_id INT NOT NULL," +
                    "appointment_date DATE NOT NULL," +
                    "time_slot VARCHAR(20) NOT NULL," +
                    "reason VARCHAR(255)," +
                    "status VARCHAR(20) DEFAULT 'SCHEDULED'," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE" +
                    ")";
            
            stmt.execute(createPatientsTable);
            stmt.execute(createDoctorsTable);
            stmt.execute(createAppointmentsTable);
            System.out.println("Tables created or already exist.");
        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
        }
    }
}
