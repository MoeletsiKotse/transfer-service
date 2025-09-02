FROM eclipse-temurin:latest

# Set a working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/transfer-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
