# Design Document - Hospital Appointment Scheduling System

## System Overview
The Hospital Appointment Scheduling System is a Java application designed to manage patient records, doctor information, and appointment scheduling in a hospital setting.

## Architecture
The system follows a layered architecture pattern:
- **Presentation Layer**: Swing GUI (MainFrame, PatientManagementFrame, DoctorManagementFrame, AppointmentManagementFrame, ReportsFrame)
- **Service Layer**: Business logic (HospitalService)
- **Data Access Layer**: Database operations (PatientDAO, DoctorDAO, AppointmentDAO)
- **Model Layer**: Entity classes (Patient, Doctor, Appointment)
- **Utility Layer**: Database utilities (DatabaseUtil)

## Class Diagrams

### Model Classes

#### Patient
- patientId: int
- firstName: String
- lastName: String
- age: int
- gender: String
- phone: String
- email: String
- address: String
- registeredDate: Date

#### Doctor
- doctorId: int
- firstName: String
- lastName: String
- specialization: String
- phone: String
- email: String
- experienceYears: int
- available: boolean

#### Appointment
- appointmentId: int
- patientId: int
- doctorId: int
- appointmentDate: Date
- timeSlot: String
- reason: String
- status: Status (enum: SCHEDULED, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW)
- createdDate: Date

## Relationships
- Patient (1) --- (many) Appointment
- Doctor (1) --- (many) Appointment
- Appointment references Patient and Doctor by IDs

## Database Design
See schema in DatabaseUtil.initializeTables()

## Design Principles
- Follows OOP principles (Encapsulation, Abstraction)
- Uses DAO pattern for data persistence
- Uses Service layer for business logic separation
- Implements proper error handling
