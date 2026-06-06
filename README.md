# 💅 Salon Booking Management System

A robust and secure backend API for a Salon Booking System. Built with **Spring Boot 3**, protected by **JWT Authentication**, and fully automated for production deployment using **Docker** and **GitHub Actions**.

## 🚀 Features
- **Role-Based Access Control (RBAC):** Admin and Customer roles.
- **JWT Security:** Secure authentication and authorization using JSON Web Tokens.
- **Service Management:** CRUD operations for salon services.
- **Beautician Management:** Manage beauticians and their specialties.
- **Schedule & Booking:** Customers can book specific time slots with chosen beauticians.
- **Database Migrations:** Automated database schema and data seeding using Liquibase.
- **Interactive API Docs:** Built-in Swagger UI for easy API testing and exploration.
- **CI/CD Pipeline:** Fully automated deployment to VPS using GitHub Actions and Docker.

---

## 🛠️ Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.4.3
- **Security:** Spring Security + JWT (jjwt)
- **Database:** MySQL 8.0
- **Migration:** Liquibase
- **Documentation:** Springdoc OpenAPI (Swagger) v2.8.9
- **Deployment:** Docker, Docker Compose, GitHub Actions

---

## 💻 Running Locally

### Prerequisites
- JDK 21 installed
- MySQL Server installed and running (Port 3306)
- Maven (or use the provided `./mvnw` wrapper)

### Setup Database
1. Create a new database in MySQL:
   ```sql
   CREATE DATABASE salon_booking_db;
   ```
2. The application will automatically connect and run Liquibase to build the tables and insert dummy data when started!

### Start the Application
Run the following command in your terminal:
```bash
./mvnw spring-boot:run
```
The server will start on `http://localhost:8081`.

---

## 📖 API Documentation (Swagger)
Once the server is running, you can interact with the API directly from your browser:
**[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)**

### How to Authenticate in Swagger:
1. Use the `/api/auth/login` endpoint.
   - **Admin:** `admin@salon.com` / `password123`
   - **Customer:** `marissa@gmail.com` / `password123`
2. Copy the `token` string from the response.
3. Scroll to the top of the Swagger page, click the green **Authorize** 🔒 button.
4. Paste your token and click **Authorize**.

---

## 🐳 Deployment to VPS

This project uses **GitHub Actions** for CI/CD. Every time code is pushed to the `main` branch, the workflow will automatically:
1. Build the Spring Boot `.jar` file.
2. Build a Docker Image and push it to Docker Hub.
3. SSH into the VPS server.
4. Pull the latest image and restart the Docker container via `docker-compose`.

### Required GitHub Secrets
To use the CI/CD pipeline, configure the following repository secrets:
- `DOCKER_HUB_USERNAME`: Your Docker Hub username
- `DOCKER_HUB_TOKEN`: Your Docker Hub access token
- `VPS_HOST`: Public IP of the VPS
- `VPS_USER`: VPS SSH username (e.g., root)
- `VPS_SSH_KEY`: Private SSH Key for the VPS

---

*Built with ❤️ for Final Project.*
