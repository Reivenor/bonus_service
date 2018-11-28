#!/usr/bin/env bash
docker pull postgres

docker run  --name postgres-test -p 5432:5432 -d postgres

#TODO docker-compose