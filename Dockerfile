# # Use a lightweight OpenJDK image
# FROM eclipse-temurin:17-jdk-alpine

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the JAR file (replace with your actual JAR name)
# COPY target/TravelPartner-user-service-0.0.1-SNAPSHOT.jar app.jar

# # Expose the application port (default for Spring Boot)
# EXPOSE 8080

# # Run the Spring Boot application
# ENTRYPOINT ["java", "-jar", "app.jar"]


# ---- Build stage ----
    FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
    WORKDIR /app
    COPY . .
    RUN mvn clean package -DskipTests
    
    # ---- Runtime stage ----
    FROM eclipse-temurin:17-jdk-alpine
    WORKDIR /app
    COPY --from=build /app/target/*.jar app.jar
    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "app.jar"]
    
