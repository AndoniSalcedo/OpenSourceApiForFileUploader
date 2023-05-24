#!/bin/bash

BASE_DIR=$(pwd)

if [ -d "$BASE_DIR/RESOURCES" ]; then
    cd "$BASE_DIR/RESOURCES"
    bash start.sh
    cd "$BASE_DIR"
fi

# Procesar todos los demás directorios
for file in $(ls -d $BASE_DIR/*)
do
    if [ -d "$file" ] && [ "$file" != "$BASE_DIR/RESOURCES" ]; then
        cd "$file"
        bash start.sh
        cd "$BASE_DIR"
    fi
done

