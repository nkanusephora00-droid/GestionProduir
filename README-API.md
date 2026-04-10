# Demo API - Backend Spring Boot

API REST complète pour la gestion d'utilisateurs, de produits et de catégories avec authentification sécurisée.

## Fonctionnalités

- **Gestion des utilisateurs** (CRUD complet)
- **Authentification sécurisée** avec hashage BCrypt
- **Gestion des rôles** (ADMIN, MANAGER, USER)
- **Gestion des produits** et catégories
- **Validation des entrées**
- **Documentation Swagger**
- **Déploiement cloud-ready**

## Endpoints Principaux

### Authentification
- `POST /api/auth/login` - Connexion
- `GET /api/auth/check-email/{email}` - Vérifier email
- `GET /api/auth/status` - Status du service

### Utilisateurs
- `GET /api/utilisateurs` - Lister tous les utilisateurs
- `POST /api/utilisateurs` - Créer un utilisateur
- `GET /api/utilisateurs/{id}` - Trouver un utilisateur
- `PUT /api/utilisateurs/{id}` - Mettre à jour
- `DELETE /api/utilisateurs/{id}` - Supprimer
- `POST /api/utilisateurs/{id}/change-password` - Changer mot de passe
- `POST /api/utilisateurs/{id}/reset-password` - Réinitialiser mot de passe

### Produits
- `GET /api/produits` - Lister tous les produits
- `POST /api/produits` - Créer un produit
- `GET /api/produits/{id}` - Trouver un produit
- `PUT /api/produits/{id}` - Mettre à jour
- `DELETE /api/produits/{id}` - Supprimer

## Documentation

- **Swagger UI**: `/swagger-ui.html`
- **API Docs**: `/v3/api-docs`
- **H2 Console**: `/h2-console` (en développement)

## Exemples d'utilisation

### Créer un utilisateur
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "motDePasse": "SecurePass123!"
  }'
```

### Authentification
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jean.dupont@example.com",
    "motDePasse": "SecurePass123!"
  }'
```

## Déploiement

### Options gratuites

1. **Heroku** (Recommandé)
   ```bash
   # Installation Heroku CLI
   npm install -g heroku
   
   # Créer l'app
   heroku create votre-app-name
   
   # Déployer
   git push heroku main
   ```

2. **Render.com**
   - Connecter votre repo GitHub
   - Configuration automatique

3. **Vercel** (pour API REST)
   - Configuration avec `vercel.json`

4. **Railway.app**
   - Déploiement direct depuis GitHub

### Configuration requise

- **Java 17**
- **Maven 3.6+**
- **Base de données**: H2 (par défaut) ou PostgreSQL

## Variables d'environnement

```bash
# Pour PostgreSQL (production)
SPRING_DATASOURCE_URL=jdbc:postgresql://host:port/database
SPRING_DATASOURCE_USERNAME=username
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

## Sécurité

- **Hashage BCrypt** des mots de passe
- **Validation stricte** des entrées
- **CORS configuré** pour les frontends
- **DTOs sécurisés** (pas de données sensibles exposées)

## Structure du projet

```
src/main/java/com/example/demo/
|-- config/          # Configuration CORS
|-- controller/      # Controllers REST
|-- dto/            # Data Transfer Objects
|-- exception/      # Gestion des erreurs
|-- mapper/         # Conversion Entité/DTO
|-- model/          # Entités JPA
|-- repository/     # Repositories Spring Data
|-- security/       # Services de sécurité
|-- service/        # Logique métier
|-- validation/     # Validateurs personnalisés
```

## Tests

```bash
# Lancer les tests
./mvnw test

# Lancer l'application
./mvnw spring-boot:run
```

## Support

Pour toute question ou problème, consultez la documentation Swagger ou contactez l'équipe de développement.

---

**API Version**: 1.0.0  
**Spring Boot**: 4.0.5  
**Java**: 17
