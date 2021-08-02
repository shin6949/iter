#!/bin/zsh
rm -rf ./target
mvn clean
mvn package
docker build --tag iter:1.0 .