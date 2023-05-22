#!/bin/bash

minikube image rm andonisalcedo/gateway:0.0.1-SNAPSHOT 2> /dev/null

echo "Uploading image gateway:0.0.1-SNAPSHOT to minikube"

minikube image load andonisalcedo/gateway:0.0.1-SNAPSHOT

minikube cache reload