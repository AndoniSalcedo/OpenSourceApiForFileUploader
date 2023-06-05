#!/bin/bash

minikube image rm andonisalcedo/config-server:1.0 2> /dev/null

echo "Uploading image config-server:1.0 to minikube"

minikube image load andonisalcedo/config-server:1.0

minikube cache reload