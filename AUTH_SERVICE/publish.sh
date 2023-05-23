#!/bin/bash

minikube image rm andonisalcedo/auth-service:1.0 2> /dev/null

echo "Uploading image auth-service:1.0 to minikube"

minikube image load andonisalcedo/auth-service:1.0

minikube cache reload