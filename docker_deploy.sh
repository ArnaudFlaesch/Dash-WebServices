#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build -t dashwebservices .
docker tag dashwebservices arnaudf93/dashwebservices:latest
docker push arnaudf93/dashwebservices:latest