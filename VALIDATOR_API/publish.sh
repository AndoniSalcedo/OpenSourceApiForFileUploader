#!/bin/bash

minikube image rm andonisalcedo/validator-api:1.0 2> /dev/null

echo "Uploading image validator-api:1.0 to minikube"

minikube image load andonisalcedo/validator-api:1.0

minikube cache reload