# Étape 1 : Builder avec Maven
FROM maven:3.9.6-eclipse-temurin-17 as builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Étape 2 : Image finale légère avec juste Java
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le .jar compilé depuis l’étape précédente
COPY --from=builder /app/target/geo-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8086
ENV SPRING_PROFILES_ACTIVE=docker

CMD ["java", "-jar", "app.jar"]
