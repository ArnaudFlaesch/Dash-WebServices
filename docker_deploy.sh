#!/bin/bash
docker login -u "$DOCKER_PASSWORD" -p "$DOCKER_USERNAME"
docker build -t dashwebservices .
docker tag dashwebservices arnaudf93/dashwebservices:latest
docker push arnaudf93/dashwebservices:latest