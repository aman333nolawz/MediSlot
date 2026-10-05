# Hospital Appointment Scheduling System

A Java-based Hospital Appointment Scheduling System built using Object-Oriented Programming (OOP) principles, Swing GUI, and JDBC for database connectivity. This project was developed as part of an OOP course.

## Features

- **Patient Management**: Register, view, update, and delete patient records
- **Doctor Management**: Add, view, update, and manage doctor profiles with specialization and availability
- **Appointment Scheduling**: Schedule, update, cancel, and manage appointments with conflict checking
- **Reports & Statistics**: View comprehensive reports and system statistics
- **Database Integration**: MySQL database connectivity using JDBC
- **User-friendly GUI**: Built with Java Swing

## Project Structure

```
hospital/
├── src/
│   ├── main/
│   │   ├── java/com/hospital/
│   │   │   ├── dao/           # Data Access Objects
│   │   │   ├── gui/           # Swing GUI components
│   │   │   ├── model/         # Entity classes
│   │   │   ├── service/       # Business logic layer
│   │   │   ├── util/          # Utility classes
│   │   │   └── Main.java      # Main entry point
│   │   └── resources/
│   └── test/
├── docs/                     # Documentation
└── build.gradle             # Gradle build file
```

## Technologies Used

- **Java 17**: Core programming language
- **Swing**: GUI framework
- **JDBC**: Database connectivity
- **MySQL**: Relational database
- **Gradle**: Build automation tool

## Database Schema

The application uses the following tables:
- `patients`: Stores patient information
- `doctors`: Stores doctor information with specialization and availability
- `appointments`: Stores appointment details with foreign key relationships

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- MySQL Server 5.7+ or 8.0+
- Gradle 7.0+ (or use Gradle wrapper)

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/aman333nolawz/MediSlot
   cd hospital
   ```

2. **Configure MySQL Database**:
   - Create a MySQL user with username `root` and password `root` (or update `DatabaseUtil.java`)
   - The application will automatically create the database and tables on startup

3. **Build the project**:
   ```bash
   gradle build
   ```

4. **Run the application**:
   ```bash
   gradle run
   ```

## OOP Principles Demonstrated

- **Encapsulation**: Private fields with getter/setter methods in model classes
- **Inheritance**: Not explicitly used but follows OOP structure
- **Polymorphism**: Method overriding (equals, hashCode, toString)
- **Abstraction**: DAO layer abstracts database operations
- **Association**: Relationships between models (Patient-Appointment-Doctor)

## Documentation

- [Design Document](docs/design.md) - Class diagrams and system design
- [User Manual](docs/user-manual.md) - Installation and usage guide
- [Test Cases](docs/test-cases.md) - Sample test cases

