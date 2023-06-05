#!/bin/bash

minikube image rm andonisalcedo/consumer-api:1.0 2> /dev/null

echo "Uploading image consumer-api:1.0 to minikube"

minikube image load andonisalcedo/consumer-api:1.0

minikube cache reload