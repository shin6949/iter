#!/bin/zsh
rm -rf ./target
mvn clean
mvn package
docker build --tag registry.architectgroup.com/iter:1.0 .