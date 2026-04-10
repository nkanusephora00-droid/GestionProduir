# Build et exécution dans une seule image
FROM maven:3.9.4-eclipse-temurin-17

WORKDIR /app

# Copier tout le projet
COPY . .

# Compiler l'application
RUN mvn clean package -DskipTests

# Variables d'environnement
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV SPRING_PROFILES_ACTIVE=production

# Port exposé
EXPOSE 8080

# Commande de démarrage
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
