#!/bin/bash

minikube image rm andonisalcedo/producer-api:1.0 2> /dev/null

echo "Uploading image producer-api:1.0 to minikube"

minikube image load andonisalcedo/producer-api:1.0

minikube cache reload