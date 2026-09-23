# =========================================================
# BorrowBox Backend — Multi-stage Dockerfile
# Java 25 build + Java 25 runtime
# =========================================================

# ---------- Stage 1: Build ----------
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

# Install Maven (Alpine doesn't include it)
RUN apk add --no-cache maven

# Copy pom.xml first (for layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy built jar from stage 1
COPY --from=build /app/target/BorrowBox-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render sets $PORT dynamically)
EXPOSE 8080

# Run with dynamic port
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]