# ---------- STAGE 1: Build the JAR ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy everything
COPY . .

# Build Spring Boot app
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run the app ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
