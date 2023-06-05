#!/bin/bash

BASE_DIR=$(pwd)

for file in $(ls -d $BASE_DIR/*) 
do
    if [ -d "$file" ]; then
        cd "$file"
        bash stop.sh
        cd "$BASE_DIR"
    fi
done

