#!/bin/bash

minikube image rm andonisalcedo/data-integrity:1.0 2> /dev/null

echo "Uploading image data-integrity:1.0 to minikube"

minikube image load andonisalcedo/data-integrity:1.0

minikube cache reload