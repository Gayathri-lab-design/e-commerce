# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/datahouse-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (optional)
EXPOSE 8212

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
