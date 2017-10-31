#!/bin/sh

# This is currently a temp solution a potential better way would be to add a gradle task which can do this.

echo 'Deleting the deployment'
kubectl delete deploy spring-starter;

sleep 15;

echo 'Deleting the docker image'
docker rmi spring-starter:v1;

echo 'Creating the jar'
gradle clean bootRepackage;

echo 'Creating the docker image'
docker build -t spring-starter:v1 .;

echo 'Creating the deployment'
kubectl create -f config/kubernetes/deployment-basic.yaml