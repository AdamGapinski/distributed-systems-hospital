#!/usr/bin/env bash

./gradlew tar
mkdir unpacked
tar -C ./unpacked -xf build/Gapiński_Adam_3.tar
cd ./unpacked/Gapiński_Adam_3/lab3-rabbitmq-hospital
./install.sh
