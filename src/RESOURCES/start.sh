#!/bin/bash

if minikube status >/dev/null 2>&1; then

  minikube ssh -- "sudo mkdir -p /mnt/ssd"

  echo "Directorio de datos creado"

  cd deploy/k8s

  bash start.sh

  cd ../../ 
else
  echo "Minikube no está en funcionamiento"
fi

