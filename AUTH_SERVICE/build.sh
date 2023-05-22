#!/bin/bash

mvn clean package docker:build

bash publish.sh
