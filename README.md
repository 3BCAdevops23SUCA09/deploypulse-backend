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

sonar analysis done:
<img width="1398" height="293" alt="image" src="https://github.com/user-attachments/assets/eda7a068-cbe8-4deb-b147-471e7e0a557c" />

⚠️ Challenges Faced & How They Were Solved (Backend)

During the development, containerization, code analysis, and deployment of the DeployPulse Backend, multiple real-world engineering and DevOps challenges were encountered. Each issue helped strengthen understanding of backend systems, Docker, cloud deployment, and build pipelines.

1️⃣ SonarCloud Analysis Showing “Not Computed”

Issue:
Even after running the SonarScanner command successfully, SonarCloud dashboard kept showing “Analysis Not Computed”.

Cause:

Incorrect organization key used initially

Sonar project was created but not properly linked with GitHub

Scan command was run incorrectly in Windows terminal (PowerShell split commands)

Solution:

Correct organization name was updated

Proper SonarCloud token generated

Correct command used in single line:

mvn clean verify sonar:sonar \
-Dsonar.projectKey=deploypulse-backend \
-Dsonar.organization=<org> \
-Dsonar.host.url=https://sonarcloud.io \
-Dsonar.token=<token>


This triggered the first successful scan, and Quality Gate appeared.

2️⃣ Docker Not Recognized in Windows

Issue:
Running docker build returned:

'docker' is not recognized as an internal or external command


Cause:
Docker Desktop was not installed and WSL2 was not enabled.

Solution:

Installed Docker Desktop for Windows (AMD64)

Enabled WSL2

Restarted system

Verified installation:

docker --version

3️⃣ Docker Port Already in Use

Issue:

ports are not available: bind: Only one usage of each socket address


Cause:
Port 8080 was already used by another Java/Spring process.

Solution:

netstat -ano | findstr :8080
taskkill /PID <PID> /F


Then container ran successfully.

4️⃣ Container Running but Browser Showing Whitelabel Error

Issue:
Clicking localhost:8080 opened Spring Boot error page.

Cause:
Backend does not have a UI root endpoint (/).
Only REST APIs exist.

Solution:
Access correct endpoint:

http://localhost:8080/api/features

5️⃣ H2 Database Not Persisting in Docker

Issue:
Data disappeared when container restarted.

Cause:
H2 was configured as in-memory database.

Solution:
Understood limitation and accepted for development use.
Future production upgrade → PostgreSQL/MySQL.

6️⃣ Docker Build Failing on Render

Issue:

COPY target/backend-0.0.1-SNAPSHOT.jar not found


Cause:
Render Docker build environment does not run Maven automatically.

Solution:
Changed to multi-stage Dockerfile:

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

7️⃣ Git Push Rejected (Fetch First)

Issue:

! [rejected] main -> main (fetch first)


Cause:
Remote repo had files (README) not present locally.

Solution:

git pull origin main --rebase
git push origin main

8️⃣ CORS Errors When Connecting Frontend

Issue:
Frontend deployed on Vercel could not access backend API.

Cause:
Spring Boot blocks cross-origin requests by default.

Solution:
Added global CORS config:

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}

9️⃣ Backend Deployment Differences (Local vs Cloud)

Issue:
Backend worked locally but failed on Render initially.

Cause:
Cloud platforms require:

Correct Dockerfile

Exposed ports

Proper start command

Solution:
Adjusted Dockerfile and Render configuration. Backend deployed successfully.

Known Limitations:

Uses in-memory DB (data resets on restart)

No authentication yet

Dev environment only

Future Enhancements:

PostgreSQL integration

Authentication & Roles

Deployment pipeline integration

Cloud deployment

#####This is for pull request checking