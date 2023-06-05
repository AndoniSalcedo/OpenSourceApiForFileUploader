#!/bin/bash

BASE_DIR=$(pwd)

# Procesar todos los demás directorios
for file in $(ls -d $BASE_DIR/*)
do
    if [ -d "$file" ] && [ "$file" != "$BASE_DIR/RESOURCES" ]; then
        cd "$file"
        mvn clean
        cd "$BASE_DIR"
    fi
done

