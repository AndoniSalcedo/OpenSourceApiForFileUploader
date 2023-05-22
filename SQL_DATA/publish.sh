#!/bin/bash

minikube image rm andonisalcedo/sql-data:1.0 2> /dev/null

echo "Uploading image sql-data:1.0 to minikube"

minikube image load andonisalcedo/sql-data:1.0

minikube cache reload