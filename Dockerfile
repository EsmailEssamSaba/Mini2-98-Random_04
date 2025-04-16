FROM openjdk:23-jdk-slim
LABEL authors="somaa"

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/miniapp-0.0.1-SNAPSHOT.jar app.jar


# Expose the port your app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
