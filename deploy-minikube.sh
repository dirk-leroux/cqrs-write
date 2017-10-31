#!/bin/sh

# This is currently not working correctly the docker image does not get removed cleanly.

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