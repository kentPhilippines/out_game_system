#!/usr/bin/env bash

mvn clean install -Dmaven.test.skip=true

docker build -t sports-api:latest .

# docker push xxxx/sports/sports-api:latest
