#!/bin/bash

minikube image rm andonisalcedo/gateway:1.0 2> /dev/null

echo "Uploading image gateway:1.0 to minikube"

minikube image load andonisalcedo/gateway:1.0

minikube cache reload