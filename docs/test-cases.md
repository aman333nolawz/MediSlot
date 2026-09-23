# Test Cases - Hospital Appointment Scheduling System

## Test Case 1: Patient Registration
- **Objective**: Verify patient registration functionality
- **Input**: Patient details (firstName="John", lastName="Doe", age=30, gender="Male", phone="1234567890")
- **Expected Output**: Patient added successfully with generated ID
- **Status**: Pass

## Test Case 2: Duplicate Time Slot Booking
- **Objective**: Prevent double booking of same doctor at same time
- **Input**: Schedule appointment for same doctor, same date, same time slot twice
- **Expected Output**: Second booking should fail with "Time slot not available" message
- **Status**: Pass

## Test Case 3: Update Patient Information
- **Objective**: Verify patient update functionality
- **Input**: Update existing patient's phone number
- **Expected Output**: Patient details updated in database
- **Status**: Pass

## Test Case 4: Cancel Appointment
- **Objective**: Verify appointment cancellation
- **Input**: Cancel existing appointment
- **Expected Output**: Status changed to CANCELLED
- **Status**: Pass

## Test Case 5: Doctor Availability Management
- **Objective**: Set doctor as unavailable
- **Input**: Update doctor availability to false
- **Expected Output**: Doctor marked as unavailable
- **Status**: Pass

## Test Case 6: View Reports
- **Objective**: Generate system reports
- **Input**: Open Reports tab
- **Expected Output**: Display all patients, doctors, appointments and statistics
- **Status**: Pass

## Test Case 7: Field Validation
- **Objective**: Validate required fields
- **Input**: Submit forms with empty required fields
- **Expected Output**: Appropriate error messages displayed
- **Status**: Pass

## Test Case 8: Date Format Validation
- **Objective**: Validate date format
- **Input**: Enter invalid date format
- **Expected Output**: Error message for invalid format
- **Status**: Pass
