# Stage 1: build all modules
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy **everything** (parent POM, both modules, source, etc.)
COPY . .

# # Build and install domain, then package app
# RUN mvn -B clean install -DskipTests
# Build domain & app, then repackage the app
RUN mvn -B clean package \
     -pl checkers-app -am \
     spring-boot:repackage \
     -DskipTests

# Stage 2: runtime image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copy the fat JAR produced by the reactor build
COPY --from=builder /app/checkers-app/target/checkers-app-*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
