#!/bin/sh

java -jar jetty-runner.jar --port 8085 target/radiator*.war > log 2>&1 &

