#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
mkdir app
docker build -t dashwebservices app
docker tag dashwebservices arnaudf93/dashwebservices:latest
docker push arnaudf93/dashwebservices:latest