#!/usr/bin/env bash

git pull
./gradlew build -x test
java -jar ./build/libs/suinCar-0.0.1-SNAPSHOT.jar
