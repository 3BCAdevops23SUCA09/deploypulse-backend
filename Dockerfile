# Use Java 21 runtime
FROM eclipse-temurin:21-jdk

# App jar name
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar

# Copy jar into container
COPY ${JAR_FILE} app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run app
ENTRYPOINT ["java","-jar","/app.jar"]
