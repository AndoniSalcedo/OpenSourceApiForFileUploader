#!/bin/bash

minikube image rm andonisalcedo/mongo-data:1.0 2> /dev/null

echo "Uploading image mongo-data:1.0 to minikube"

minikube image load andonisalcedo/mongo-data:1.0

minikube cache reload