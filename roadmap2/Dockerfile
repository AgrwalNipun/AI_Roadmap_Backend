# ===============================
# BUILD STAGE (Java 21)
# ===============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================
# RUNTIME STAGE (Java 21)
# ===============================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the built JAR from build stage
COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
