FROM docker.io/eclipse-temurin:25.0.3_9-jdk-alpine-3.23 AS builder
WORKDIR /app

# Create caching layer for maven dependency
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

# Build the application
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Run Step
FROM docker.io/eclipse-temurin:25.0.3_9-jre-alpine-3.23
COPY --from=builder /app/target/realworld-example.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
