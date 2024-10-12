FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/gestion_evento_microservicio-0.0.1-SNAPSHOT.jar /app/event-server.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/event-server.jar"]