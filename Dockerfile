# ============================================
# Dockerfile Template - Java/Spring Application
# ============================================
# 
# INSTRUCTIONS:
# 1. Copy this file to your project root as "Dockerfile"
# 2. Update the comments marked with TODO
# 3. Adjust commands for your specific project
#
# ============================================

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Create non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -S appuser -u 1001

WORKDIR /app

# Copy the built jar from build stage
# TODO: Update the jar file name to match your project
COPY --from=build /app/target/*.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

# TODO: Change to your application's port
EXPOSE 8080

# Health check
# TODO: Update the health check URL to match your application
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-8080}/actuator/health || exit 1

# TODO: Add any JVM options your application needs
CMD ["java", "-jar", "app.jar"]
