FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy only the inner Invoice_Backendd folder contents
COPY Invoice_Backendd/ .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
