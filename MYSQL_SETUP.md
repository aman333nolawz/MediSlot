# MySQL Setup Guide

## Option 1: Using Docker (Recommended)

1. Start MySQL using Docker Compose:
```bash
docker compose up -d
```

## Current Configuration

The app expects:
- Host: `localhost:3306`
- Database: `hospital_db`
- Username: `root`
- Password: `root`

These are configured in `src/main/java/com/hospital/util/DatabaseUtil.java`. You can modify them if needed.
