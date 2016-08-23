#!/usr/bin/env bash
set -e

cd lab-repo

mvn clean test -Djava.version=1.7
