#!/bin/bash
docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD"
docker build -t dashwebservices .
docker tag dashwebservices arnaudf93/dashwebservices:latest
docker push arnaudf93/dashwebservices:latest