# MonteeEnCompetence - Plateforme Web Full Stack

Ce projet est une application web Full Stack de gestion de montée en compétences, basée sur une architecture moderne avec **Angular**, **Spring Boot**, **JHipster**, **PostgreSQL**, **Keycloak** et **Docker**.

## 📂 Structure du projet

```
monteeencompetence/
├── monteecompetencefront/   # Frontend Angular généré par JHipster
├── monteecompetenceback/    # Backend Spring Boot (JHipster)
       
```

---

## 🚀 Technologies principales

| Composant        | Stack                              |
| ---------------- | ---------------------------------- |
| Frontend         | Angular 17, Bootstrap, JHipster    |
| Backend          | Spring Boot 3, JHipster, Hibernate |
| Authentification | Keycloak (OIDC)                    |
| Base de données  | PostgreSQL                         |
| Infrastructure   | Docker, Docker Compose             |

---

## 🔧 Prérequis

* [Node.js](https://nodejs.org/) >= 18
* [Docker](https://www.docker.com/)
* [Java 17](https://adoptium.net/)
* [JHipster CLI](https://www.jhipster.tech/installation/) :

```bash
npm install -g generator-jhipster
```

---

## 🌐 Déploiement local avec Docker Compose

1. Clonez le projet :

```bash
git clone git@github.com:votreutilisateur/monteeencompetence.git
cd monteeencompetence
```

2. Générez les images ou utilisez celles existantes :

```bash
docker-compose -f docker/app.yml up --build
```

3. Accédez aux services :

| Service     | URL                                            |
| ----------- | ---------------------------------------------- |
| Frontend    | [http://localhost:9000](http://localhost:9000) |
| Backend API | [http://localhost:8080](http://localhost:8080) |
| Keycloak    | [http://localhost:9080](http://localhost:9080) |
| PostgreSQL  | localhost:5432                                 |

---

## 🛠️ Backend (Spring Boot / JHipster)

### Démarrer en mode dev

```bash
cd monteecompetenceback
./mvnw
```

* Accès Swagger : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Configuration de Keycloak

* Keycloak est configuré comme fournisseur OIDC
* Les rôles ("ROLE\_ADMIN", "ROLE\_USER") sont synchronisés automatiquement
* Voir `src/main/resources/config/application.yml` pour l'URL du serveur d'authentification

---

## 🌐 Frontend (Angular)

### Démarrer en mode dev

```bash
cd monteecompetencefront
npm install
npm start
```

* Accès : [http://localhost:9000](http://localhost:9000)
* Proxy vers l'API backend configuré automatiquement

---

## 🔐 Authentification avec Keycloak

* L'application utilise OAuth2 / OIDC avec Keycloak.
* Un rôle d'admin est requis pour accéder aux fonctions avancées (via la console d'administration de Keycloak).

### Utilisateurs de test

| Nom d'utilisateur | Mot de passe | Rôle        |
| ----------------- | ------------ | ----------- |
| admin             | admin        | ROLE\_ADMIN |
| user              | user         | ROLE\_USER  |

---

## 📈 Fonctionnalités principales

* Authentification et gestion des utilisateurs via Keycloak
* Tableau de bord des compétences
* Attribution d'activités / formations
* Visualisation des progrès
* Exports (PDF/CSV)

---

## 🚧 Déploiement cloud (optionnel)

Vous pouvez déployer l'application sur :

* **Heroku** (via GitHub Actions)
* **AWS ECS** ou **EC2** (Docker)
* **Kubernetes** : un fichier `k8s/` peut être ajouté pour le déploiement

---

## 🚫 Problèmes connus

* Keycloak doit être démarré **avant** le backend pour assurer la synchronisation
* Les ports peuvent être ajustés dans `docker/app.yml` si conflits

---

## 📄 License

Ce projet est sous licence MIT.

---


> Ce projet combine les bonnes pratiques DevOps, développement web moderne et d'authentification sécurisée avec Keycloak pour une application full-stack robuste et scalable.
