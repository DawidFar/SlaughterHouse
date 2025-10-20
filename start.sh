#!/bin/bash
# Quick launcher: ensure DB env vars are exported, then build & run
mvn clean package
java -jar target/slaughterhouse-grpc-1.0-SNAPSHOT.jar
