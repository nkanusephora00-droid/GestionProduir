#!/bin/bash

# Script de build pour l'application Spring Boot
echo "=== Build de l'application Spring Boot ==="

# Variables
APP_NAME="demo"
VERSION=${1:-latest}
REGISTRY=${REGISTRY:-localhost:5000}
DOCKERFILE="deployment/Dockerfile"

# Nettoyage des anciennes images et conteneurs
echo "Nettoyage des anciennes ressources..."
docker-compose down --remove-orphans 2>/dev/null || true
docker system prune -f

# Build de l'image Docker
echo "Build de l'image Docker..."
docker build -f $DOCKERFILE -t $APP_NAME:$VERSION -t $APP_NAME:latest ..
if [ $? -ne 0 ]; then
    echo "ERREUR: Le build Docker a échoué"
    exit 1
fi

# Tag pour le registre (optionnel)
if [ ! -z "$REGISTRY" ] && [ "$REGISTRY" != "localhost:5000" ]; then
    echo "Tag pour le registre $REGISTRY..."
    docker tag $APP_NAME:$VERSION $REGISTRY/$APP_NAME:$VERSION
    docker tag $APP_NAME:latest $REGISTRY/$APP_NAME:latest
fi

echo "=== Build terminé avec succès ==="
echo "Image créée: $APP_NAME:$VERSION"

# Affichage des images
docker images | grep $APP_NAME
