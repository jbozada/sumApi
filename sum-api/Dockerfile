# Etapa 1: compila el JAR
FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY . .
RUN mvn clean package

# Etapa 2: imagen ligera para ejecutar el JAR
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/sum-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
