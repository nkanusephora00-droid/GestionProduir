# Étape 1: Build de l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2: Image de production avec JRE
FROM eclipse-temurin:17-jre-alpine

# Créer un utilisateur non-root pour la sécurité
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Copier le JAR compilé
COPY --from=build /app/target/*.jar app.jar

# Créer le répertoire de travail
WORKDIR /app

# Changer le propriétaire des fichiers
RUN chown -R spring:spring /app
USER spring

# Variables d'environnement
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"
ENV SPRING_PROFILES_ACTIVE=prod

# Port exposé
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
