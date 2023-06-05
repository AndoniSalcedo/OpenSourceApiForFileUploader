#!/bin/bash

minikube image rm andonisalcedo/worker:1.0 2> /dev/null

echo "Uploading image worker:1.0 to minikube"

minikube image load andonisalcedo/worker:1.0

minikube cache reload