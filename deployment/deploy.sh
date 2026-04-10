#!/bin/bash

# Script de déploiement pour l'application Spring Boot
echo "=== Déploiement de l'application Spring Boot ==="

# Variables
APP_NAME="demo"
VERSION=${1:-latest}
ENVIRONMENT=${2:-dev}
COMPOSE_FILE="deployment/docker-compose.yml"

# Vérification de Docker
if ! command -v docker &> /dev/null; then
    echo "ERREUR: Docker n'est pas installé"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "ERREUR: Docker Compose n'est pas installé"
    exit 1
fi

# Build si nécessaire
if [ "$3" = "--build" ] || [ ! "$(docker images -q $APP_NAME:$VERSION 2> /dev/null)" ]; then
    echo "Build de l'image..."
    ./deployment/build.sh $VERSION
fi

# Arrêt des anciens conteneurs
echo "Arrêt des anciens conteneurs..."
docker-compose -f $COMPOSE_FILE down --remove-orphans

# Démarrage selon l'environnement
case $ENVIRONMENT in
    "prod")
        echo "Déploiement en mode production..."
        docker-compose -f $COMPOSE_FILE --profile postgres --profile redis --profile nginx up -d
        ;;
    "staging")
        echo "Déploiement en mode staging..."
        docker-compose -f $COMPOSE_FILE --profile postgres up -d
        ;;
    "dev"|*)
        echo "Déploiement en mode développement..."
        docker-compose -f $COMPOSE_FILE up -d
        ;;
esac

# Attente du démarrage
echo "Attente du démarrage de l'application..."
sleep 10

# Vérification du statut
echo "Vérification du statut..."
docker-compose -f $COMPOSE_FILE ps

# Test de santé
echo "Test de santé de l'application..."
for i in {1..30}; do
    if curl -f http://localhost:8080/actuator/health &> /dev/null; then
        echo "Application démarrée avec succès !"
        break
    fi
    echo "En attente... ($i/30)"
    sleep 2
done

# Affichage des URLs
echo ""
echo "=== URLs disponibles ==="
echo "Application: http://localhost:8080"
echo "Swagger UI: http://localhost:8080/swagger-ui.html"
echo "API Docs: http://localhost:8080/v3/api-docs"
echo "H2 Console: http://localhost:8080/h2-console"

if [ "$ENVIRONMENT" = "prod" ] || [ "$ENVIRONMENT" = "staging" ]; then
    echo "PostgreSQL: localhost:5432"
fi

if [ "$ENVIRONMENT" = "prod" ]; then
    echo "Nginx: http://localhost"
    echo "Redis: localhost:6379"
fi

echo ""
echo "=== Déploiement terminé ==="
