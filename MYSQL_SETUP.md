# MySQL Setup Guide

## Option 1: Using Docker (Recommended)

1. Start MySQL using Docker Compose:
```bash
cd /home/nolawz/Coding/java/hospital
docker compose up -d
```

2. Verify MySQL is running:
```bash
docker compose ps
```

3. Stop MySQL when done:
```bash
docker compose down
```

## Option 2: Local MySQL Installation

1. Install MySQL Server 8.0
2. Create user `root` with password `root` or update `src/main/java/com/hospital/util/DatabaseUtil.java`
3. Ensure MySQL is running on port 3306

## Current Configuration

The app expects:
- Host: `localhost:3306`
- Database: `hospital_db`
- Username: `root`
- Password: `root`

These are configured in `src/main/java/com/hospital/util/DatabaseUtil.java`. You can modify them if needed.
