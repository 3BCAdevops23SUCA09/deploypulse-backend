DeployPulse Backend

A Spring Boot backend service for managing software feature tracking and deployment status.

Project Summary

DeployPulse is a full-stack DevOps tracking system designed to monitor feature development, build status, and deployment readiness.

This repository contains the Spring Boot backend, which exposes REST APIs consumed by the React frontend.

Backend live demo:https://deploypulse-backend.onrender.com/api/features
deployed using render.

Architecture Overview
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (React App)                     │
│                                                             │
│  ├── Feature Dashboard                                      │
│  ├── Status Tracking UI                                     │
│  └── Deployment Monitoring                                   │
│                                                             │
│          ↕ REST API Communication (JSON over HTTP)         │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                 Backend (Spring Boot API)                   │
│                       DeployPulse Service                   │
│                                                             │
│  ├── REST Controllers                                       │
│  ├── Business Logic Layer                                   │
│  ├── JPA/Hibernate Data Layer                               │
│  └── Exception Handling                                     │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                      H2 Database                            │
│                   (In-Memory Database)                      │
└─────────────────────────────────────────────────────────────┘

Frontend & Backend Communication

Protocol: REST over HTTP
Data Format: JSON
Base URL (Local):

http://localhost:8080

Example Flow

Frontend Request

GET /api/features


Backend Processing

FeatureController receives request

FeatureService processes logic

FeatureRepository fetches data from H2

JSON response returned

Response

[
  {
    "id": 1,
    "name": "Login Module",
    "owner": "Imraan",
    "status": "IN_DEVELOPMENT",
    "buildStatus": "PASSED",
    "approved": false
  }
]

Technology Stack
Technology	Version
Java	21
Spring Boot	3.5.x
Spring Data JPA	✔
Hibernate	6.x
H2 Database	In-Memory
Maven Wrapper	mvnw
Docker	Containerized
Project Structure
backend/
├── src/main/java/com/deploypulse/backend
│   ├── BackendApplication.java
│   ├── controller/FeatureController.java
│   ├── service/FeatureService.java
│   ├── repository/FeatureRepository.java
│   ├── model/Feature.java
│   └── exception/
├── src/main/resources/application.properties
├── Dockerfile
└── pom.xml

Features

Feature tracking system

Build status monitoring (PASSED / FAILED / NOT_RUN)

Deployment readiness tracking

CRUD operations

H2 database console

Dockerized backend

Running Locally
1️⃣ Build
mvnw.cmd clean package

2️⃣ Run
mvnw.cmd spring-boot:run


App runs at:

http://localhost:8080

H2 Database Console
http://localhost:8080/h2-console

Setting	Value
JDBC URL	jdbc:h2:mem:deploypulse
Username	sa
Password	(empty)
API Endpoints
Method	Endpoint	Description
GET	/api/features	Get all features
GET	/api/features/{id}	Get feature by ID
POST	/api/features	Create feature
PUT	/api/features/{id}	Update feature
DELETE	/api/features/{id}	Delete feature
Docker Setup
Build Image
docker build -t deploypulse-backend .

Run Container
docker run -d -p 8080:8080 --name deploypulse deploypulse-backend

Challenges Faced & Solutions
1. Port 8080 Already in Use

Issue: Docker container failed to start.
Cause: Spring Boot app already running locally.
Solution:

netstat -ano | findstr :8080
taskkill /PID <PID> /F

2. Docker Daemon Not Running

Error:

failed to connect to dockerDesktopLinuxEngine


Cause: Docker Desktop not started.
Fix: Start Docker Desktop before running containers.

3. White Label Error Page

Cause: Visiting root URL / with no controller mapping.
Fix: Use correct API endpoint:

http://localhost:8080/api/features

4. H2 Console Link Confusion

Correct URL:

http://localhost:8080/h2-console


Known Limitations:

Uses in-memory DB (data resets on restart)

No authentication yet

Dev environment only

Future Enhancements:

PostgreSQL integration

Authentication & Roles

Deployment pipeline integration

Cloud deployment
