#!/bin/bash

minikube image rm andonisalcedo/{ARTIFACT}:{VERSION} 2> /dev/null

echo "Uploading image {ARTIFACT}:{VERSION} to minikube"

minikube image load andonisalcedo/{ARTIFACT}:{VERSION}

minikube cache reload