# Étape 1 : Build avec Maven dans une image dédiée
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copier pom.xml et les sources
COPY pom.xml .
COPY src ./src

# Télécharger les dépendances et compiler (sans tests)
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution légère
FROM openjdk:17-jdk-slim

# Métadonnées
LABEL maintainer="elbikariizineb@gmail.com"
LABEL service="annonce-service"

WORKDIR /app

# Copier uniquement le JAR généré
COPY --from=builder /app/target/annonce-service-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port du service
EXPOSE 8082

# Profil Spring actif
ENV SPRING_PROFILES_ACTIVE=docker

# Lancer le service
ENTRYPOINT ["java", "-jar", "app.jar"]
