# Use OpenJDK 21 slim
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy Maven wrapper and config
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

# Download dependencies for caching
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the app (skip tests)
RUN ./mvnw clean package -DskipTests

# Create uploads dir for image uploads
RUN mkdir -p /app/uploads/images

# Expose backend port
EXPOSE 8080

# Run Spring Boot with docker profile
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "target/Backend-0.0.1-SNAPSHOT.jar"]
