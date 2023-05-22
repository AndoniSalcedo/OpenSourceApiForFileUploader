#!/bin/bash

minikube image rm andonisalcedo/process-service:1.0 2> /dev/null

echo "Uploading image process-service:1.0 to minikube"

minikube image load andonisalcedo/process-service:1.0

minikube cache reload