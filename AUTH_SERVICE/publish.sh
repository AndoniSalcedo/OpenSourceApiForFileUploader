#!/bin/bash

minikube image rm andonisalcedo/AUTH_SERVICE:0.0.1-SNAPSHOT 2> /dev/null

echo "Uploading image AUTH_SERVICE:0.0.1-SNAPSHOT to minikube"

minikube image load andonisalcedo/AUTH_SERVICE:0.0.1-SNAPSHOT

minikube cache reload