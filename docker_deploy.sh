#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build -t dashservices .
docker tag dashservices arnaudf93/dashwebservices:latest
docker push arnaudf93/dashwebservices:latest