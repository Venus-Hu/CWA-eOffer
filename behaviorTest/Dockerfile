# Use an official Maven image as a base image
FROM maven:3.6.3-openjdk-8

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file into the container at /app
COPY pom.xml .

# Run Maven to download the dependencies
RUN mvn dependency:go-offline

# The rest of your application code
COPY . .

# Command to run your application
CMD ["mvn", "clean test"]


FROM jenkins/jenkins:latest

USER root

# Install Maven
RUN apt-get update && apt-get install -y maven

USER jenkins
