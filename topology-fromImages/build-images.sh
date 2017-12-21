#!/bin/bash
cd ../docker
echo "******************** Building the jar ********************"
cd microservice-status
./build-jar.sh
cd ..

echo "******************** Building the image ********************"
docker build --rm -t pestakit/status microservice-status

echo "******************** Building the mysql image ********************"
docker build --rm -t pestakit/status-db mysql

echo

