# Dossier de Déploiement

Ce dossier contient tous les fichiers nécessaires pour déployer votre application Spring Boot en production.

## Structure

```
deployment/
|-- Dockerfile              # Configuration de l'image Docker
|-- docker-compose.yml      # Orchestration des services
|-- .dockerignore          # Fichiers à exclure de Docker
|-- build.sh               # Script de build
|-- deploy.sh              # Script de déploiement
|-- nginx.conf             # Configuration Nginx
|-- init.sql               # Script d'initialisation PostgreSQL
|-- README.md              # Ce fichier
```

## Services Disponibles

### 1. Application Spring Boot
- Port: 8080
- Base de données: H2 (par défaut) ou PostgreSQL
- Health check: `/actuator/health`

### 2. PostgreSQL (optionnel)
- Port: 5432
- Base: `demo`
- User: `demo_user`
- Password: `demo_password`

### 3. Redis (optionnel)
- Port: 6379
- Utilisé pour le cache

### 4. Nginx (optionnel)
- Ports: 80, 443
- Reverse proxy avec rate limiting
- Compression gzip

## Utilisation

### Build de l'image
```bash
./deployment/build.sh [version]
```

### Déploiement

#### Développement
```bash
./deployment/deploy.sh latest dev
```

#### Staging (avec PostgreSQL)
```bash
./deployment/deploy.sh latest staging
```

#### Production (complet)
```bash
./deployment/deploy.sh latest prod
```

### Avec build automatique
```bash
./deployment/deploy.sh latest prod --build
```

## URLs après déploiement

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console
- **Nginx (prod)**: http://localhost

## Commandes utiles

### Vérifier les conteneurs
```bash
docker-compose -f deployment/docker-compose.yml ps
```

### Voir les logs
```bash
docker-compose -f deployment/docker-compose.yml logs -f demo-app
```

### Arrêter tout
```bash
docker-compose -f deployment/docker-compose.yml down
```

### Nettoyer
```bash
docker system prune -f
```

## Configuration

### Variables d'environnement
- `SPRING_PROFILES_ACTIVE`: Profile Spring (dev/prod)
- `SPRING_DATASOURCE_URL`: URL de la base de données
- `JAVA_OPTS`: Options JVM

### Profiles Docker
- `postgres`: Active PostgreSQL
- `redis`: Active Redis
- `nginx`: Active Nginx reverse proxy

## Sécurité

- Utilisateur non-root dans les conteneurs
- Rate limiting sur Nginx
- Headers de sécurité HTTP
- Compression gzip activée

## Monitoring

- Health checks automatiques
- Logs structurés
- Métriques Spring Actuator (si activé)

## Déploiement en production

1. Modifier les configurations de production
2. Activer les profiles nécessaires
3. Configurer SSL (certificats dans dossier `ssl/`)
4. Utiliser un registre Docker privé
5. Configurer CI/CD

## Dépannage

### Problèmes communs
- **Port déjà utilisé**: `netstat -tulpn | grep :8080`
- **Build échoue**: Vérifier les dépendances Maven
- **Base de données**: Vérifier les credentials

### Logs détaillés
```bash
# Logs de l'application
docker logs demo-spring-boot

# Logs de tous les services
docker-compose -f deployment/docker-compose.yml logs
```
