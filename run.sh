#!/bin/sh
kill $(ps aux | grep 'radiator-2.0-SNAPSHOT.war' | awk '{print $2}')
java -jar jetty-runner.jar --port 8085 target/radiator*.war > log 2>&1 &

