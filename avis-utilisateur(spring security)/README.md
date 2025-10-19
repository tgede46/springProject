# Avis Utilisateur

Ce projet est une API REST Spring Boot pour la gestion des utilisateurs, des avis et de la validation par code.

## Fonctionnalités principales
- Inscription d'utilisateur avec validation par code (6 chiffres)
- Authentification sécurisée (Spring Security)
- Gestion des rôles (utilisateur, etc.)
- Création et gestion d'avis
- Validation d'email par code envoyé

## Technologies utilisées
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- MariaDB
- Lombok

## Structure du projet
```
avis-utilisateur/
├── src/main/java/gedeon/net/avis/
│   ├── AvisUtilisateurApplication.java
│   ├── entite/         # Entités JPA (Utilisateur, Avis, Role, Validation)
│   ├── controlleur/    # Contrôleurs REST
│   ├── service/        # Services métier
│   ├── repository/     # Repositories JPA
│   └── securite/       # Configuration sécurité
├── src/main/resources/
│   ├── application.properties
│   └── ...
├── pom.xml
└── README.md
```


## Lancer le projet
1. Démarrer MariaDB (voir `docker-compose.yml` si besoin)
2. Compiler et lancer l’application :
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
3. L’API sera disponible sur : `http://localhost:8080/api`

## Exemple d’inscription
- **URL** : `POST /api/inscription`
- **Body JSON** :
```json
{
  "nom": "gedeon",
  "email": "gedeonkpara@gmail.com",
  "mdp": "gedeo"
}
```

## Auteur
- gedeonkp
