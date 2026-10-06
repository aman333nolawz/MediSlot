# User Manual - Hospital Appointment Scheduling System

## System Requirements
- Java Runtime Environment (JRE) 17 or higher
- MySQL Server 5.7 or higher

## Installation Guide

### 1. Database Setup
1. Install and start MySQL Server
2. Create a user 'root' with password 'root' (or modify DatabaseUtil.java)
3. The application will create the database automatically on first run

### 2. Running the Application
```bash
gradle run
```

## User Guide

### Main Dashboard
The main window provides 4 options:
1. Patient Management - Manage patient records
2. Doctor Management - Manage doctor profiles
3. Appointment Management - Schedule and manage appointments
4. Reports - View reports and statistics

### Patient Management
- **Add Patient**: Fill in patient details (First Name, Last Name, Age, Gender, Phone, Email, Address)
- **Update Patient**: Select a patient from table, modify fields, click Update
- **Delete Patient**: Select a patient, click Delete, confirm action
- **Clear**: Clear all form fields
- **Refresh**: Reload patient list

### Doctor Management
- **Add Doctor**: Fill in doctor details including specialization and availability
- **Update Doctor**: Select and update doctor information
- **Delete Doctor**: Remove doctor record
- **Availability Toggle**: Mark doctor as available/unavailable

### Appointment Management
- **Schedule Appointment**: Select patient, doctor, date, time slot, reason
- **Check Availability**: Verify if time slot is free for selected doctor
- **Update Appointment**: Modify appointment details or status
- **Cancel Appointment**: Cancel without deleting (sets status to CANCELLED)
- **Delete Appointment**: Permanently remove appointment

### Reports
- View all patients, doctors, and appointments in tabular format
- View system statistics (total counts)

## Tips
- Date format: YYYY-MM-DD
- Time slot format: HH:MM-HH:MM (e.g., 09:00-10:00)
- Required fields are marked clearly
- Time slot conflicts are automatically checked
