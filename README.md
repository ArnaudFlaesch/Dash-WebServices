# Dash-WebServices

[![CI](https://github.com/ArnaudFlaesch/Dash-WebServices/actions/workflows/ci.yml/badge.svg)](https://github.com/ArnaudFlaesch/Dash-WebServices/actions)
[![codecov](https://codecov.io/gh/ArnaudFlaesch/Dash-WebServices/branch/master/graph/badge.svg)](https://codecov.io/gh/ArnaudFlaesch/Dash-WebServices)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ArnaudFlaesch_Dash-WebServices&metric=alert_status)](https://sonarcloud.io/dashboard?id=ArnaudFlaesch_Dash-WebServices)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ArnaudFlaesch_Dash-WebServices&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ArnaudFlaesch_Dash-WebServices)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ArnaudFlaesch_Dash-WebServices&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ArnaudFlaesch_Dash-WebServices)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ArnaudFlaesch_Dash-WebServices&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=ArnaudFlaesch_Dash-WebServices)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/a596f0ee2ba346fe8fdce1381476f078)](https://app.codacy.com/gh/ArnaudFlaesch/Dash-WebServices/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/a596f0ee2ba346fe8fdce1381476f078)](https://app.codacy.com/gh/ArnaudFlaesch/Dash-WebServices/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)

## Description

L'application a pour but de proposer à un utilisateur de créer un dashboard sur
lequel il peut créer plusieurs widgets, comme par exemple un lecteur de flux
RSS, un calendrier listant des évènements à partir d'un lien iCal et un
affichage de prévisions météorologiques. Il est possible de répartir ces
widgets sur plusieurs onglets.

Ce dépôt contient les sources de la partie backend de l'application, développée
en Kotlin avec Spring Boot et une base de données PostgreSQL.

- Frontend (Github Pages) : <https://arnaudflaesch.github.io/Dash-Web/>
- Documentation de l'API (Swagger UI) :
  <https://arnaudflaesch.github.io/Dash-WebServices/>
- Le backend est déployé sur Google Cloud Run à chaque push sur `master`.

## Prérequis

- JDK 25
- Docker

## Démarrage

En local, l'application utilise le profil par défaut : les appels aux API
externes sont redirigés vers le serveur de mock
[Dash-MockServer](https://github.com/ArnaudFlaesch/Dash-MockServer), il n'est
donc pas nécessaire de disposer de clés d'API.

- Démarrage de la base de données

```bash
docker run --name dash-db -p 5432:5432 -d -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dash postgres:18.6-alpine3.24
```

- Démarrage du serveur de mock

```bash
docker run --name dash-mockserver -p 5000:5000 -d ghcr.io/arnaudflaesch/dash-mockserver:latest
```

- Démarrage du projet (le mot de passe de la base de données est lu depuis la
  variable d'environnement `POSTGRES_PASSWORD`)

```bash
POSTGRES_PASSWORD=postgres ./gradlew bootRun
```

L'API est alors accessible sur <http://localhost:8080> et sa documentation
OpenAPI sur <http://localhost:8080/api-docs>.

## Tests

Les tests utilisent le profil `test`, qui se connecte à la base `dash_test` et
au serveur de mock.

- Création de la base de test (avec la base démarrée ci-dessus)

```bash
docker exec dash-db createdb -U postgres dash_test
```

- Exécution des tests

```bash
./gradlew test
```

- Rapport de couverture (généré dans `build/reports/kover`)

```bash
./gradlew koverHtmlReport
```

- Lint des fichiers sources

```bash
./gradlew lintKotlin
```

- Correction automatique du lint

```bash
./gradlew formatKotlin
```

## Configuration

La configuration se trouve dans `src/main/resources/application.yml`, avec un
profil par environnement :

| Profil       | Utilisation                                                       |
|--------------|-------------------------------------------------------------------|
| (défaut)     | Développement local, base `dash` et serveur de mock               |
| `test`       | Tests et génération de la documentation OpenAPI, base `dash_test` |
| `production` | Déploiement sur Cloud Run, secrets lus dans GCP Secret Manager    |

Les versions des dépendances et des plugins Gradle sont centralisées dans
`gradle/libs.versions.toml` et mises à jour par Renovate.

### Secrets de production

En production, les secrets suivants sont lus dans GCP Secret Manager :

| Secret                                                     | Lien pour le récupérer                                                |
|------------------------------------------------------------|-----------------------------------------------------------------------|
| `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`   | Base de données PostgreSQL                                            |
| `JWT_SECRET`                                               | Clé encodée en Base64 pour signer les tokens JWT                      |
| `OPENWEATHERMAP_KEY`                                       | <https://home.openweathermap.org/api_keys>                            |
| `STEAM_API_KEY`                                            | <https://steamcommunity.com/dev/apikey>                               |
| `STRAVA_CLIENT_ID`, `STRAVA_CLIENT_SECRET`                 | <https://www.strava.com/settings/api>                                 |
| `AIRPARIF_API_TOKEN`                                       | <https://www.airparif.asso.fr/interface-de-programmation-applicative> |

## Docker

```bash
docker build -t dash-webservices .
```

### Coverage

![Coverage](https://codecov.io/gh/ArnaudFlaesch/Dash-WebServices/branch/master/graphs/sunburst.svg)
