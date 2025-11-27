# --------------------------
# Stage 1: Build the application using Maven
# --------------------------

#  Use Maven image with Java 21; name this stage "builder"

FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Create and move into /app directory inside the container

COPY pom.xml .

# Copy only the pom.xml first for dependency caching

RUN mvn dependency:go-offline -B

# Download all dependencies in advance (faster future builds)

COPY src ./src

# Copy the source code into the container

RUN mvn clean package -DskipTests

# Build the project and create a jar file (skip tests for speed)



# --------------------------
# Stage 2: Run the built application using a lighter Java image
# --------------------------

FROM openjdk:26-ea-oraclelinux8 AS runner


# Use a lightweight Java 21 image to run the app; name it "runner"

WORKDIR /app

# Create and switch to /app directory

COPY --from=builder /app/target/*.jar ./app.jar

# Copy the jar from the builder stage into this stage and rename it app.jar

EXPOSE 4000

# Declare that the app will run on port 4000 (documentation for Docker)

ENTRYPOINT ["java", "-jar", "app.jar"]

# Command that will run when the container starts — runs the Java application
