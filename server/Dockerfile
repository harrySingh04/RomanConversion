FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Step 2: Use a smaller JDK base image to run the JAR
FROM amazoncorretto:17
WORKDIR /app

# Copy the built JAR from the Maven build stage
COPY --from=build /app/target/*.jar /app/romanconversion.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/romanconversion.jar"]

