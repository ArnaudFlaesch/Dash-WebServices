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
lequel il peut créer plusieurs widgets,comme par exemple un lecteur de flux RSS,
un calendrier listant des évènements à partir d'un lien ICal et un affichage
de prévisions météorologiques. Il est possible de répartir ces widgets sur
plusieurs onglets.

Le projet est déployé via Github Pages et accessible à cette
addresse : <https://arnaudflaesch.github.io/Dash-Web/>.
Ce dépôt contient les sources de la partie backend de l'application, qui est
accessible à l'addresse : <https://dash-webservices.herokuapp.com/>.

## Démarrage

- Récupération de l'image Docker de la base de données

> docker pull postgres:16.3-alpine3.20

- Démarrage de la base de données

> docker run -p 5432:5432 -d -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dash
> postgres:16.3-alpine3.20

- Variables d'environnement

Le projet a besoin de plusieurs variables d'environnement pour s'exécuter,
principalement des clés d'API pour accéder à des services externes.

| Nom de la variable d'environnement | Lien pour la récupérer                                                |
|------------------------------------|-----------------------------------------------------------------------|
| OPENWEATHERMAP_KEY                 | <https://home.openweathermap.org/api_keys>                            |
| STEAM_API_KEY                      | <https://steamcommunity.com/dev/apikey>                               |
| STRAVA_CLIENT_ID                   | <https://www.strava.com/settings/api>                                 |
| STRAVA_CLIENT_SECRET               | <https://www.strava.com/settings/api>                                 |
| AIRPARIF_API_TOKEN                 | <https://www.airparif.asso.fr/interface-de-programmation-applicative> |

- Démarrage du projet

> ./gradlew bootRun

## Commandes utiles

- Exécution des tests

> ./gradlew test

- Lint des fichiers sources

> ./gradlew lintKotlin

### Coverage

![Coverage](https://codecov.io/gh/ArnaudFlaesch/Dash-WebServices/branch/master/graphs/sunburst.svg)
