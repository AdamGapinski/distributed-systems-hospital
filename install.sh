#!/usr/bin/env bash

./gradlew clean build
tar -xf ./administrator/build/distributions/administrator-1.0.tar
tar -xf ./orthopedist/build/distributions/orthopedist-1.0.tar
tar -xf ./technician/build/distributions/technician-1.0.tar
ln -s ./administrator-1.0/bin/administrator administrator.sh
ln -s ./orthopedist-1.0/bin/orthopedist orthopedist.sh
ln -s ./technician-1.0/bin/technician technician.sh
